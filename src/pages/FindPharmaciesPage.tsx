import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Search, MapPin, Phone, Star, Clock, Filter } from 'lucide-react';

interface Pharmacy {
  id: string;
  name: string;
  image: string;
  address: string;
  phone: string;
  rating: number;
  openingHours: string;
  division: string;
  district: string;
  city: string;
  features: string[];
  established: number;
  deliveryAvailable: boolean;
  emergencyService: boolean;
  type: 'retail' | 'wholesale' | 'chain';
}

const generatePharmacies = (): Pharmacy[] => {
  const features = [
    '24/7 Service',
    'Home Delivery',
    'Online Prescription',
    'Vaccination Service',
    'Health Checkup',
    'Medicine Counseling',
    'Baby Care Products',
    'Diabetes Care',
    'Personal Care',
    'Health Supplements'
  ];

  const divisions = [
    'Dhaka',
    'Chittagong',
    'Rajshahi',
    'Khulna',
    'Barisal',
    'Sylhet',
    'Rangpur',
    'Mymensingh'
  ];

  const districts = {
    'Dhaka': ['Dhaka', 'Gazipur', 'Narayanganj'],
    'Chittagong': ['Chittagong', "Cox's Bazar", 'Comilla'],
    'Rajshahi': ['Rajshahi', 'Bogra', 'Pabna'],
    'Khulna': ['Khulna', 'Jessore', 'Kushtia'],
    'Barisal': ['Barisal', 'Bhola', 'Patuakhali'],
    'Sylhet': ['Sylhet', 'Moulvibazar', 'Habiganj'],
    'Rangpur': ['Rangpur', 'Dinajpur', 'Kurigram'],
    'Mymensingh': ['Mymensingh', 'Jamalpur', 'Netrokona']
  };

  const cities = {
    'Dhaka': ['Uttara', 'Gulshan', 'Mirpur', 'Dhanmondi', 'Banani', 'Mohammadpur'],
    'Gazipur': ['Gazipur Sadar', 'Tongi', 'Sreepur'],
    'Chittagong': ['Agrabad', 'Nasirabad', 'Halishahar', 'GEC Circle']
  };

  const pharmacies: Pharmacy[] = [];
  const pharmacyNames = [
    'HealthFirst Pharmacy',
    'MediCare Plus',
    'Wellness Pharma',
    'LifeCare Pharmacy',
    'Family Health Pharmacy',
    'Green Cross Pharmacy',
    'Modern Medicine Center',
    'Care & Cure Pharmacy',
    'City Pharmacy',
    'Union Pharmacy'
  ];

  for (let i = 0; i < 50; i++) {
    const division = divisions[Math.floor(Math.random() * divisions.length)];
    const district = districts[division as keyof typeof districts][Math.floor(Math.random() * districts[division as keyof typeof districts].length)];
    const city = cities[district as keyof typeof cities]?.[Math.floor(Math.random() * (cities[district as keyof typeof cities]?.length || 1))] || district;

    const randomFeatures = [...features]
      .sort(() => Math.random() - 0.5)
      .slice(0, Math.floor(Math.random() * 5) + 3);

    const basePharmacyName = pharmacyNames[i % pharmacyNames.length];
    const pharmacyName = i < pharmacyNames.length 
      ? basePharmacyName 
      : `${basePharmacyName} ${Math.floor(i / pharmacyNames.length) + 1}`;

    pharmacies.push({
      id: `pharm-${i + 1}`,
      name: pharmacyName,
      image: 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=800&q=80',
      address: `${Math.floor(Math.random() * 200) + 1}, ${city}, ${district}`,
      phone: `+880 ${Math.floor(Math.random() * 2) + 1}${Math.random().toString().slice(2, 11)}`,
      rating: Number((Math.random() * (5 - 3.5) + 3.5).toFixed(1)),
      openingHours: Math.random() > 0.3 ? '24/7' : '9:00 AM - 11:00 PM',
      division,
      district,
      city,
      features: randomFeatures,
      established: Math.floor(Math.random() * (2023 - 1990) + 1990),
      deliveryAvailable: Math.random() > 0.2,
      emergencyService: Math.random() > 0.4,
      type: Math.random() > 0.7 ? 'chain' : Math.random() > 0.5 ? 'retail' : 'wholesale'
    });
  }

  return pharmacies;
};

const pharmacies = generatePharmacies();

export function FindPharmaciesPage() {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedLocation, setSelectedLocation] = useState({
    division: '',
    district: '',
    city: ''
  });
  const [selectedFeatures, setSelectedFeatures] = useState<string[]>([]);
  const [selectedType, setSelectedType] = useState<'all' | 'retail' | 'wholesale' | 'chain'>('all');

  const divisions = [
    'Dhaka',
    'Chittagong',
    'Rajshahi',
    'Khulna',
    'Barisal',
    'Sylhet',
    'Rangpur',
    'Mymensingh'
  ];

  const districts = {
    'Dhaka': ['Dhaka', 'Gazipur', 'Narayanganj'],
    'Chittagong': ['Chittagong', "Cox's Bazar", 'Comilla']
  };

  const cities = {
    'Dhaka': ['Uttara', 'Gulshan', 'Mirpur', 'Dhanmondi'],
    'Gazipur': ['Gazipur Sadar', 'Tongi', 'Sreepur']
  };

  const features = [
    '24/7 Service',
    'Home Delivery',
    'Online Prescription',
    'Vaccination Service',
    'Health Checkup',
    'Medicine Counseling'
  ];

  const filteredPharmacies = pharmacies.filter(pharmacy => {
    const matchesSearch = 
      pharmacy.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      pharmacy.address.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesLocation = (
      (!selectedLocation.division || pharmacy.division === selectedLocation.division) &&
      (!selectedLocation.district || pharmacy.district === selectedLocation.district) &&
      (!selectedLocation.city || pharmacy.city === selectedLocation.city)
    );

    const matchesFeatures = selectedFeatures.length === 0 || 
      selectedFeatures.every(feature => pharmacy.features.includes(feature));

    const matchesType = selectedType === 'all' || pharmacy.type === selectedType;

    return matchesSearch && matchesLocation && matchesFeatures && matchesType;
  });

  const handleCall = (phone: string) => {
    window.location.href = `tel:${phone}`;
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">Find Pharmacies</h1>
          <p className="text-gray-600 max-w-2xl mx-auto">
            Discover pharmacies near you. Get information about services, operating hours, and contact details.
          </p>
        </div>

        <div className="bg-white rounded-xl shadow-lg p-6 mb-12">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Search pharmacies by name or location..."
                className="w-full pl-12 pr-4 py-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedType}
              onChange={(e) => setSelectedType(e.target.value as 'all' | 'retail' | 'wholesale' | 'chain')}
            >
              <option value="all">All Types</option>
              <option value="retail">Retail Pharmacy</option>
              <option value="wholesale">Wholesale Pharmacy</option>
              <option value="chain">Chain Pharmacy</option>
            </select>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedLocation.division}
              onChange={(e) => setSelectedLocation({
                ...selectedLocation,
                division: e.target.value,
                district: '',
                city: ''
              })}
            >
              <option value="">Select Division</option>
              {divisions.map(division => (
                <option key={division} value={division}>{division}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedLocation.district}
              onChange={(e) => setSelectedLocation({
                ...selectedLocation,
                district: e.target.value,
                city: ''
              })}
              disabled={!selectedLocation.division}
            >
              <option value="">Select District</option>
              {selectedLocation.division && districts[selectedLocation.division as keyof typeof districts]?.map(district => (
                <option key={district} value={district}>{district}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedLocation.city}
              onChange={(e) => setSelectedLocation({
                ...selectedLocation,
                city: e.target.value
              })}
              disabled={!selectedLocation.district}
            >
              <option value="">Select City</option>
              {selectedLocation.district && cities[selectedLocation.district as keyof typeof cities]?.map(city => (
                <option key={city} value={city}>{city}</option>
              ))}
            </select>
          </div>

          <div className="flex flex-wrap gap-2">
            {features.map(feature => (
              <button
                key={feature}
                className={`px-3 py-1 rounded-full text-sm ${
                  selectedFeatures.includes(feature)
                    ? 'bg-blue-500 text-white'
                    : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                }`}
                onClick={() => setSelectedFeatures(prev =>
                  prev.includes(feature)
                    ? prev.filter(f => f !== feature)
                    : [...prev, feature]
                )}
              >
                {feature}
              </button>
            ))}
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredPharmacies.map((pharmacy) => (
            <div
              key={pharmacy.id}
              className="bg-white rounded-lg shadow-sm overflow-hidden hover:shadow-md transition-shadow"
            >
              <img
                src={pharmacy.image}
                alt={pharmacy.name}
                className="w-full h-48 object-cover"
              />
              <div className="p-6">
                <div className="flex items-center justify-between mb-2">
                  <h3 className="font-semibold text-lg">{pharmacy.name}</h3>
                  <div className="flex items-center gap-1">
                    <Star className="w-5 h-5 text-yellow-500 fill-current" />
                    <span>{pharmacy.rating}</span>
                  </div>
                </div>

                <div className="space-y-2 text-sm text-gray-600 mb-4">
                  <div className="flex items-center gap-2">
                    <MapPin size={16} />
                    <span>{pharmacy.address}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Phone size={16} />
                    <span>{pharmacy.phone}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Clock size={16} />
                    <span>{pharmacy.openingHours}</span>
                  </div>
                </div>

                <div className="flex flex-wrap gap-2 mb-4">
                  {pharmacy.features.map((feature, index) => (
                    <span
                      key={index}
                      className="px-2 py-1 bg-blue-50 text-blue-600 rounded-full text-xs"
                    >
                      {feature}
                    </span>
                  ))}
                </div>

                <div className="flex items-center justify-between text-sm mb-4">
                  <span className={`px-2 py-1 rounded-full ${
                    pharmacy.type === 'chain'
                      ? 'bg-purple-100 text-purple-600'
                      : pharmacy.type === 'wholesale'
                      ? 'bg-blue-100 text-blue-600'
                      : 'bg-green-100 text-green-600'
                  }`}>
                    {pharmacy.type.charAt(0).toUpperCase() + pharmacy.type.slice(1)}
                  </span>
                  {pharmacy.established && (
                    <span className="text-gray-500">Est. {pharmacy.established}</span>
                  )}
                </div>

                <div className="flex flex-wrap gap-2">
                  {pharmacy.deliveryAvailable && (
                    <span className="px-2 py-1 bg-green-100 text-green-600 rounded-full text-xs">
                      Delivery Available
                    </span>
                  )}
                  {pharmacy.emergencyService && (
                    <span className="px-2 py-1 bg-red-100 text-red-600 rounded-full text-xs">
                      24/7 Emergency
                    </span>
                  )}
                </div>

                <button
                  onClick={() => handleCall(pharmacy.phone)}
                  className="w-full mt-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors flex items-center justify-center gap-2"
                >
                  <Phone size={16} />
                  Call Now
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}