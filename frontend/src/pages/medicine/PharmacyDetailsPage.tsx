import { useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { ArrowLeft, MapPin, Phone, Clock, Search, ShoppingCart } from 'lucide-react';

interface Medicine {
  id: string;
  name: string;
  manufacturer: string;
  price: number;
  inStock: boolean;
  description: string;
  category: string;
  image: string;
}

interface Pharmacy {
  id: string;
  name: string;
  address: string;
  phone: string;
  rating: number;
  openingHours: string;
  image: string;
  medicines: Medicine[];
}

const MOCK_PHARMACY: Pharmacy = {
  id: '1',
  name: 'HealthFirst Pharmacy',
  address: '123 Medical Center Road, Dhaka',
  phone: '+880 1234-567890',
  rating: 4.8,
  openingHours: '9:00 AM - 10:00 PM',
  image: 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=800&h=400',
  medicines: [
    {
      id: '1',
      name: 'Paracetamol 500mg',
      manufacturer: 'Healthcare Pharma',
      price: 50,
      inStock: true,
      description: 'Pain reliever and fever reducer',
      category: 'Pain Relief',
      image: 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&w=200&h=200'
    },
    {
      id: '2',
      name: 'Amoxicillin 500mg',
      manufacturer: 'MediCare Ltd',
      price: 120,
      inStock: true,
      description: 'Antibiotic for bacterial infections',
      category: 'Antibiotics',
      image: 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&w=200&h=200'
    },
    {
      id: '3',
      name: 'Omeprazole 20mg',
      manufacturer: 'LifeCare Pharmaceuticals',
      price: 80,
      inStock: false,
      description: 'For acid reflux and heartburn',
      category: 'Digestive Health',
      image: 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&w=200&h=200'
    }
  ]
};

export function PharmacyDetailsPage() {
  const { id } = useParams<{ id: string }>();
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');

  const categories = ['All', 'Pain Relief', 'Antibiotics', 'Digestive Health'];

  const filteredMedicines = MOCK_PHARMACY.medicines.filter(medicine => {
    const matchesSearch = medicine.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      medicine.manufacturer.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = selectedCategory.toLowerCase() === 'all' || medicine.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-6xl mx-auto">
        <div className="flex items-center mb-8">
          <Link to="/medicine/pharmacies" className="flex items-center text-gray-600 hover:text-gray-800">
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back to Pharmacies
          </Link>
        </div>

        <div className="bg-white rounded-lg shadow-sm overflow-hidden">
          <img
            src={MOCK_PHARMACY.image}
            alt={MOCK_PHARMACY.name}
            className="w-full h-64 object-cover"
          />

          <div className="p-6">
            <div className="flex justify-between items-start mb-6">
              <div>
                <h1 className="text-2xl font-bold mb-2">{MOCK_PHARMACY.name}</h1>
                <div className="space-y-2 text-gray-600">
                  <div className="flex items-center gap-2">
                    <MapPin size={18} />
                    <span>{MOCK_PHARMACY.address}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Phone size={18} />
                    <span>{MOCK_PHARMACY.phone}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Clock size={18} />
                    <span>{MOCK_PHARMACY.openingHours}</span>
                  </div>
                </div>
              </div>
              <div className="bg-green-100 text-green-600 px-4 py-1 rounded-full">
                Open Now
              </div>
            </div>

            <div className="border-t pt-6">
              <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-6">
                <div className="relative flex-1">
                  <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                  <input
                    type="text"
                    placeholder="Search medicines..."
                    className="w-full pl-10 pr-4 py-2 rounded-lg border-gray-200 focus:ring-blue-500 focus:border-blue-500"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                  />
                </div>
                <div className="flex gap-2">
                  {categories.map((category) => (
                    <button
                      key={category}
                      className={'px-4 py-2 rounded-full ' + (
                        selectedCategory === category
                          ? 'bg-blue-500 text-white'
                          : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                      )}
                      onClick={() => setSelectedCategory(category)}
                    >
                      {category}
                    </button>
                  ))}
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {filteredMedicines.map((medicine) => (
                  <div key={medicine.id} className="border rounded-lg p-4">
                    <div className="flex items-start gap-4">
                      <img
                        src={medicine.image}
                        alt={medicine.name}
                        className="w-20 h-20 object-cover rounded-lg"
                      />
                      <div className="flex-1">
                        <h3 className="font-semibold mb-1">{medicine.name}</h3>
                        <p className="text-sm text-gray-600 mb-1">{medicine.manufacturer}</p>
                        <p className="text-sm text-gray-600 mb-2">{medicine.description}</p>
                        <div className="flex items-center justify-between">
                          <span className="font-semibold text-lg">৳{medicine.price}</span>
                          <button
                            className={'px-3 py-1 rounded-full flex items-center gap-1 ' + (
                              medicine.inStock
                                ? 'bg-blue-500 text-white hover:bg-blue-600'
                                : 'bg-gray-100 text-gray-400 cursor-not-allowed'
                            )}
                            disabled={!medicine.inStock}
                          >
                            <ShoppingCart size={16} />
                            <span>{medicine.inStock ? 'Add to Cart' : 'Out of Stock'}</span>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}