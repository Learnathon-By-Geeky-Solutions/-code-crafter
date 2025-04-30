import { useEffect, useState } from 'react';
import { Search, MapPin, Phone, Clock } from 'lucide-react';
import { api } from '../lib/api';

interface Pharmacy {
  id: number;
  pharmacyName: string;
  phone: string;
  email: string | null;
  area: string | null;
  upazilaName: string | null;
  districtName: string | null;
  divisionName: string | null;
}

export function FindPharmaciesPage() {
  const [pharmacies, setPharmacies] = useState<Pharmacy[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedArea, setSelectedArea] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    fetchPharmacies();
  }, [page]);

  const fetchPharmacies = async () => {
    try {
      const res = await api.get(`/api/v1/pharmacy?page=${page}&size=10&sortBy=id&sortDir=DESC`);
      if (res.data.code === 'XS0001') {
        setPharmacies(res.data.data.content);
        setTotalPages(res.data.data.totalPages);
      }
    } catch (err) {
      console.error('Failed to fetch pharmacies:', err);
    }
  };

  const filteredPharmacies = pharmacies.filter(pharmacy => {
    const matchSearch =
      pharmacy.pharmacyName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      (pharmacy.area?.toLowerCase().includes(searchTerm.toLowerCase()) ?? false);

    const matchArea = !selectedArea || pharmacy.upazilaName === selectedArea;

    return matchSearch && matchArea;
  });

  const handleCall = (phone: string) => {
    window.location.href = `tel:${phone}`;
  };

  const uniqueAreas = Array.from(new Set(pharmacies.map(p => p.upazilaName).filter(Boolean)));

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">Find Pharmacies</h1>
          <p className="text-gray-600 max-w-2xl mx-auto">
            Discover pharmacies near you. Get address, contact details, and location.
          </p>
        </div>

        <div className="bg-white rounded-xl shadow-lg p-6 mb-12">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Search pharmacies by name or area..."
                className="w-full pl-12 pr-4 py-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedArea}
              onChange={(e) => setSelectedArea(e.target.value)}
            >
              <option value="">All Areas</option>
              {uniqueAreas.map((area, idx) => (
                <option key={idx} value={area!}>{area}</option>
              ))}
            </select>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredPharmacies.map(pharmacy => (
            <div
              key={pharmacy.id}
              className="bg-white rounded-lg shadow-sm overflow-hidden hover:shadow-md transition-shadow"
            >
              <img
                src="https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=800&q=80"
                alt="Pharmacy"
                className="w-full h-48 object-cover"
              />
              <div className="p-6">
                <h3 className="font-semibold text-lg mb-2">{pharmacy.pharmacyName}</h3>
                <div className="space-y-2 text-sm text-gray-600 mb-4">
                  <div className="flex items-center gap-2">
                    <MapPin size={16} />
                    <span>{pharmacy.area || `${pharmacy.upazilaName || ''}, ${pharmacy.districtName || ''}, ${pharmacy.divisionName || ''}`}</span>
                  </div>
                  {pharmacy.email && (
                    <div className="flex items-center gap-2">
                      <span className="font-medium">Email:</span>
                      <span>{pharmacy.email}</span>
                    </div>
                  )}
                </div>
                <div className="flex items-center justify-between">
                  <span className="px-3 py-1 bg-blue-100 text-blue-600 text-xs rounded-full">24/7 Service</span>
                  <button
                    onClick={() => handleCall(pharmacy.phone)}
                    className="flex items-center gap-2 bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded text-sm"
                  >
                    <Phone size={16} /> Call Now
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Pagination */}
        <div className="mt-10 flex justify-center items-center gap-4">
          <button
            onClick={() => setPage(prev => Math.max(prev - 1, 0))}
            disabled={page === 0}
            className="px-4 py-2 bg-gray-200 text-gray-700 rounded disabled:opacity-50"
          >
            Previous
          </button>
          <span>Page {page + 1} of {totalPages}</span>
          <button
            onClick={() => setPage(prev => prev + 1)}
            disabled={page + 1 >= totalPages}
            className="px-4 py-2 bg-gray-200 text-gray-700 rounded disabled:opacity-50"
          >
            Next
          </button>
        </div>
      </div>
    </div>
  );
}
