import { useEffect, useState } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import { Search, MapPin, Phone, Clock, Truck, Heart, Users, Building2, Siren, Stethoscope, Star, ArrowLeft } from 'lucide-react';
import { api } from '../lib/api'; // 
import { AmbulanceType } from '../types';

interface Ambulance {
  id: number;
  user: {
    fname: string;
    lname: string;
    phone: string;
    email: string;
    area: string;
    upazila: {
      name: string;
      district: {
        name: string;
        division: {
          name: string;
        };
      };
    };
  };
  ambulanceType: string;
  ambulanceNumber: string;
  ambulanceStatus: string;
  about: string;
  service_offers: string;
  hospital_affiliation: string;
  coverage_areas: string;
  response_time: number;
  doctors: number;
  nurses: number;
  paramedics: number;
  team_qualification: string;
  starting_fee: number;
}

export function AmbulanceListPage() {
  const { type } = useParams<{ type: AmbulanceType }>();
  const navigate = useNavigate();

  const [ambulances, setAmbulances] = useState<Ambulance[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedDivision, setSelectedDivision] = useState('');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [selectedUpazila, setSelectedUpazila] = useState('');
  const [availability, setAvailability] = useState('all');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAmbulances();
  }, [type]);

  const fetchAmbulances = async () => {
    try {
      setLoading(true);
      const { data: baseResponse } = await api.get('/api/v1/ambulance/list', {
        params: {
          type: type?.toUpperCase(),
          page: 0,
          size: 100,
          sortBy: 'id',
          direction: 'asc',
        },
      });

      if (baseResponse.code === 'XS0001') {
        setAmbulances(baseResponse.data.ambulances.content);
      }
    } catch (error) {
      console.error('Failed to fetch ambulances', error);
    } finally {
      setLoading(false);
    }
  };

  const divisions = [...new Set(ambulances.map(a => a.user.upazila.district.division.name))];
  const districts = selectedDivision
    ? [...new Set(ambulances.filter(a => a.user.upazila.district.division.name === selectedDivision).map(a => a.user.upazila.district.name))]
    : [];
  const upazilas = selectedDistrict
    ? [...new Set(ambulances.filter(a => a.user.upazila.district.name === selectedDistrict).map(a => a.user.upazila.name))]
    : [];

  const filteredAmbulances = ambulances.filter(ambulance => {
    const matchesSearch =
      ambulance.user.fname.toLowerCase().includes(searchTerm.toLowerCase()) ||
      ambulance.user.area.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesDivision = !selectedDivision || ambulance.user.upazila.district.division.name === selectedDivision;
    const matchesDistrict = !selectedDistrict || ambulance.user.upazila.district.name === selectedDistrict;
    const matchesUpazila = !selectedUpazila || ambulance.user.upazila.name === selectedUpazila;
    const matchesAvailability = availability === 'all' || ambulance.ambulanceStatus === availability.toUpperCase();

    return matchesSearch && matchesDivision && matchesDistrict && matchesUpazila && matchesAvailability;
  });

  const randomImage = "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=800&q=80";

  return (
    <div className="min-h-screen bg-gradient-to-b from-red-50 to-white">
      {/* Hero Section */}
      <div className="relative h-[300px] overflow-hidden">
        <img
          src={randomImage}
          alt="Ambulance"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-red-900/90 to-red-800/75 flex items-center">
          <div className="container mx-auto px-4">
            <Link to="/ambulance" className="flex items-center text-white/80 hover:text-white mb-4">
              <ArrowLeft className="w-5 h-5 mr-2" />
              Back to Ambulance
            </Link>
            <h1 className="text-4xl font-bold text-white mb-2 capitalize">{type} Ambulances</h1>
            <p className="text-white/90 text-lg">Find the right ambulance with advanced facilities</p>
          </div>
        </div>
      </div>

      {/* Filters Section */}
      <div className="container mx-auto px-4">
        <div className="relative z-10 -mt-20">
          <div className="bg-white rounded-2xl shadow-xl p-6 md:p-8 space-y-6">

            {/* Full Width Search */}
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Search by hospital or area..."
                className="pl-12 pr-4 py-3 rounded-xl w-full bg-gray-100 border border-gray-300 focus:ring-2 focus:ring-red-300 transition"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>

            {/* Dropdown Filters */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <select
                value={selectedDivision}
                onChange={(e) => {
                  setSelectedDivision(e.target.value);
                  setSelectedDistrict('');
                  setSelectedUpazila('');
                }}
                className="p-3 rounded-xl w-full bg-gray-100 border border-gray-300 focus:ring-2 focus:ring-red-300 transition"
              >
                <option value="">Division</option>
                {divisions.map(div => (
                  <option key={div} value={div}>{div}</option>
                ))}
              </select>

              <select
                value={selectedDistrict}
                onChange={(e) => {
                  setSelectedDistrict(e.target.value);
                  setSelectedUpazila('');
                }}
                disabled={!selectedDivision}
                className="p-3 rounded-xl w-full bg-gray-100 border border-gray-300 focus:ring-2 focus:ring-red-300 transition disabled:bg-gray-200"
              >
                <option value="">District</option>
                {districts.map(dist => (
                  <option key={dist} value={dist}>{dist}</option>
                ))}
              </select>

              <select
                value={selectedUpazila}
                onChange={(e) => setSelectedUpazila(e.target.value)}
                disabled={!selectedDistrict}
                className="p-3 rounded-xl w-full bg-gray-100 border border-gray-300 focus:ring-2 focus:ring-red-300 transition disabled:bg-gray-200"
              >
                <option value="">Upazila</option>
                {upazilas.map(upa => (
                  <option key={upa} value={upa}>{upa}</option>
                ))}
              </select>
            </div>

          </div>
        </div>
      </div>

      {/* Ambulance List */}
      <div className="container mx-auto px-4 py-12 grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        {loading ? (
          <div className="col-span-full text-center text-gray-400">Loading ambulances...</div>
        ) : filteredAmbulances.length === 0 ? (
          <div className="col-span-full text-center text-gray-400">No ambulances found</div>
        ) : (
          filteredAmbulances.map((a) => (
            <div
              key={a.id}
              className="bg-white rounded-xl overflow-hidden shadow hover:shadow-lg transform hover:-translate-y-1 transition-all duration-300 cursor-pointer"
              
              onClick={() => navigate(`/ambulance/details/${a.id}`)}



            >
              <div className="h-48 bg-gray-100">
                <img
                  src={randomImage}
                  alt={a.user.fname}
                  className="w-full h-full object-cover"
                />
              </div>
              <div className="p-4">
                <h3 className="text-lg font-semibold">{a.user.fname} {a.user.lname}</h3>
                <p className="text-sm text-gray-500 mb-2">{a.user.area}, {a.user.upazila.name}</p>

                <div className="text-sm mb-2 flex flex-wrap gap-2">
                  <span className="px-2 py-1 bg-blue-50 text-blue-600 rounded-full text-xs">
                    {a.ambulanceType}
                  </span>
                  <span className="px-2 py-1 bg-green-50 text-green-600 rounded-full text-xs">
                    {a.ambulanceStatus}
                  </span>
                </div>

                <div className="flex justify-between items-center mt-4">
                  <div>
                    <div className="text-red-500 font-semibold">৳ {a.starting_fee}</div>
                    <div className="text-xs text-gray-400">Starting Fee</div>
                  </div>
                  <button className="px-4 py-2 bg-red-500 text-white rounded-full hover:bg-red-600 transition-all text-sm">
                    View Details
                  </button>
                </div>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}
