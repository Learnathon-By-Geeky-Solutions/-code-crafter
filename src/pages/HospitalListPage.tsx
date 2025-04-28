import { ArrowLeft, Search, MapPin, Phone, Filter, Star, Clock, Building2, Users, Stethoscope, Ambulance, Heart } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';

interface Hospital {
  id: string;
  name: string;
  image: string;
  address: string;
  phone: string;
  rating: number;
  openHours: string;
  division: string;
  district: string;
  city: string;
  features: string[];
  doctorCount: number;
  bedCount: number;
  emergencyService: boolean;
  ambulanceService: boolean;
  specialties: string[];
  establishedYear: number;
  type: 'government' | 'private';
}

const hospitals: Hospital[] = [
  {
    id: '1',
    name: 'City General Hospital',
    image: 'https://images.unsplash.com/photo-1587351021759-3e566b6af7cc?auto=format&fit=crop&w=800&h=400&q=80',
    address: '123 Medical Center Drive, Gulshan-1',
    phone: '+880 1234-567890',
    rating: 4.8,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'CCU', 'Emergency', 'Pharmacy', 'Laboratory'],
    doctorCount: 150,
    bedCount: 500,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Cardiology', 'Neurology', 'Orthopedics'],
    establishedYear: 1990,
    type: 'private'
  },
  {
    id: '2',
    name: 'Central Medical Center',
    image: 'https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?auto=format&fit=crop&w=800&h=400&q=80',
    address: '456 Healthcare Avenue, Chittagong Medical',
    phone: '+880 1234-567891',
    rating: 4.7,
    openHours: '24/7',
    division: 'Chittagong',
    district: 'Chittagong',
    city: 'Chittagong City',
    features: ['ICU', 'Emergency', 'Pharmacy', 'Diagnostic'],
    doctorCount: 120,
    bedCount: 400,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Pediatrics', 'Gynecology', 'Surgery'],
    establishedYear: 1995,
    type: 'government'
  },
  {
    id: '3',
    name: 'Square Hospitals Ltd',
    image: 'https://images.unsplash.com/photo-1586773860418-d37222d8fce3?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'West Panthapath, Dhaka',
    phone: '+880 1234-567892',
    rating: 4.9,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'CCU', 'NICU', 'Emergency', 'Pharmacy'],
    doctorCount: 200,
    bedCount: 600,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Cardiology', 'Oncology', 'Neurosurgery'],
    establishedYear: 2006,
    type: 'private'
  },
  {
    id: '4',
    name: 'Dhaka Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1580582932707-520aed937b7b?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Secretariat Road, Dhaka',
    phone: '+880 1234-567893',
    rating: 4.5,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['Emergency', 'Surgery', 'Laboratory', 'Training'],
    doctorCount: 300,
    bedCount: 800,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['General Medicine', 'Surgery', 'Gynecology'],
    establishedYear: 1946,
    type: 'government'
  },
  {
    id: '5',
    name: 'Apollo Hospitals Dhaka',
    image: 'https://images.unsplash.com/photo-1596541223130-5d31a73fb6c6?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Plot-81, Block-E, Bashundhara R/A',
    phone: '+880 1234-567894',
    rating: 4.8,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'CCU', 'NICU', 'Emergency', 'Pharmacy'],
    doctorCount: 180,
    bedCount: 450,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Cardiology', 'Neurology', 'Oncology'],
    establishedYear: 2005,
    type: 'private'
  },
  {
    id: '6',
    name: 'Chittagong Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1538108149393-fbbd81895907?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'O.R. Nizam Road, Chittagong',
    phone: '+880 1234-567895',
    rating: 4.6,
    openHours: '24/7',
    division: 'Chittagong',
    district: 'Chittagong',
    city: 'Chittagong City',
    features: ['Emergency', 'Surgery', 'Laboratory'],
    doctorCount: 250,
    bedCount: 700,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['General Medicine', 'Surgery', 'Pediatrics'],
    establishedYear: 1957,
    type: 'government'
  },
  {
    id: '7',
    name: 'United Hospital Limited',
    image: 'https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Plot-15, Road-71, Gulshan',
    phone: '+880 1234-567896',
    rating: 4.7,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'CCU', 'Emergency', 'Pharmacy'],
    doctorCount: 160,
    bedCount: 500,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Cardiology', 'Orthopedics', 'Neurology'],
    establishedYear: 2006,
    type: 'private'
  },
  {
    id: '8',
    name: 'Evercare Hospital Dhaka',
    image: 'https://images.unsplash.com/photo-1586773860418-d37222d8fce3?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Plot-81, Block-E, Bashundhara',
    phone: '+880 1234-567897',
    rating: 4.8,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'CCU', 'NICU', 'Emergency'],
    doctorCount: 170,
    bedCount: 400,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Cardiology', 'Oncology', 'Neurology'],
    establishedYear: 2004,
    type: 'private'
  },
  {
    id: '9',
    name: 'Rajshahi Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1580582932707-520aed937b7b?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Rajshahi',
    phone: '+880 1234-567898',
    rating: 4.5,
    openHours: '24/7',
    division: 'Rajshahi',
    district: 'Rajshahi',
    city: 'Rajshahi City',
    features: ['Emergency', 'Surgery', 'Laboratory'],
    doctorCount: 200,
    bedCount: 600,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['General Medicine', 'Surgery', 'Pediatrics'],
    establishedYear: 1958,
    type: 'government'
  },
  {
    id: '10',
    name: 'Ibn Sina Hospital',
    image: 'https://images.unsplash.com/photo-1596541223130-5d31a73fb6c6?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'House-48, Road-9/A, Dhanmondi',
    phone: '+880 1234-567899',
    rating: 4.6,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'Emergency', 'Pharmacy'],
    doctorCount: 120,
    bedCount: 300,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Internal Medicine', 'Surgery', 'Gynecology'],
    establishedYear: 1983,
    type: 'private'
  },
  {
    id: '11',
    name: 'Sylhet MAG Osmani Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1538108149393-fbbd81895907?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Sylhet',
    phone: '+880 1234-567900',
    rating: 4.4,
    openHours: '24/7',
    division: 'Sylhet',
    district: 'Sylhet',
    city: 'Sylhet City',
    features: ['Emergency', 'Surgery', 'Laboratory'],
    doctorCount: 180,
    bedCount: 500,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['General Medicine', 'Surgery', 'Orthopedics'],
    establishedYear: 1962,
    type: 'government'
  },
  {
    id: '12',
    name: 'Labaid Specialized Hospital',
    image: 'https://images.unsplash.com/photo-1587351021759-3e566b6af7cc?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'House-6, Road-4, Dhanmondi',
    phone: '+880 1234-567901',
    rating: 4.7,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'CCU', 'Emergency', 'Pharmacy'],
    doctorCount: 140,
    bedCount: 350,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Cardiology', 'Nephrology', 'Neurology'],
    establishedYear: 2004,
    type: 'private'
  },
  {
    id: '13',
    name: 'Khulna Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1580582932707-520aed937b7b?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Khulna',
    phone: '+880 1234-567902',
    rating: 4.5,
    openHours: '24/7',
    division: 'Khulna',
    district: 'Khulna',
    city: 'Khulna City',
    features: ['Emergency', 'Surgery', 'Laboratory'],
    doctorCount: 160,
    bedCount: 450,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['General Medicine', 'Surgery', 'Gynecology'],
    establishedYear: 1969,
    type: 'government'
  },
  {
    id: '14',
    name: 'Popular Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1596541223130-5d31a73fb6c6?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Dhanmondi, Dhaka',
    phone: '+880 1234-567903',
    rating: 4.6,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'Emergency', 'Pharmacy'],
    doctorCount: 130,
    bedCount: 300,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Internal Medicine', 'Pediatrics', 'Surgery'],
    establishedYear: 2006,
    type: 'private'
  },
  {
    id: '15',
    name: 'Rangpur Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1538108149393-fbbd81895907?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Rangpur',
    phone: '+880 1234-567904',
    rating: 4.4,
    openHours: '24/7',
    division: 'Rangpur',
    district: 'Rangpur',
    city: 'Rangpur City',
    features: ['Emergency', 'Surgery', 'Laboratory'],
    doctorCount: 150,
    bedCount: 400,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['General Medicine', 'Surgery', 'Pediatrics'],
    establishedYear: 1970,
    type: 'government'
  },
  {
    id: '16',
    name: 'Green Life Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1587351021759-3e566b6af7cc?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Green Road, Dhaka',
    phone: '+880 1234-567905',
    rating: 4.5,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'Emergency', 'Pharmacy'],
    doctorCount: 120,
    bedCount: 250,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Internal Medicine', 'Surgery', 'Gynecology'],
    establishedYear: 2009,
    type: 'private'
  },
  {
    id: '17',
    name: 'Mymensingh Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1580582932707-520aed937b7b?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Mymensingh',
    phone: '+880 1234-567906',
    rating: 4.4,
    openHours: '24/7',
    division: 'Mymensingh',
    district: 'Mymensingh',
    city: 'Mymensingh City',
    features: ['Emergency', 'Surgery', 'Laboratory'],
    doctorCount: 170,
    bedCount: 500,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['General Medicine', 'Surgery', 'Pediatrics'],
    establishedYear: 1962,
    type: 'government'
  },
  {
    id: '18',
    name: 'Anwer Khan Modern Hospital',
    image: 'https://images.unsplash.com/photo-1596541223130-5d31a73fb6c6?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Dhanmondi, Dhaka',
    phone: '+880 1234-567907',
    rating: 4.6,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'Emergency', 'Pharmacy'],
    doctorCount: 110,
    bedCount: 200,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Internal Medicine', 'Surgery', 'Cardiology'],
    establishedYear: 2008,
    type: 'private'
  },
  {
    id: '19',
    name: 'Barisal Medical College Hospital',
    image: 'https://images.unsplash.com/photo-1538108149393-fbbd81895907?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Medical College Road, Barisal',
    phone: '+880 1234-567908',
    rating: 4.3,
    openHours: '24/7',
    division: 'Barisal',
    district: 'Barisal',
    city: 'Barisal City',
    features: ['Emergency', 'Surgery', 'Laboratory'],
    doctorCount: 140,
    bedCount: 350,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['General Medicine', 'Surgery', 'Gynecology'],
    establishedYear: 1968,
    type: 'government'
  },
  {
    id: '20',
    name: 'Japan Bangladesh Friendship Hospital',
    image: 'https://images.unsplash.com/photo-1587351021759-3e566b6af7cc?auto=format&fit=crop&w=800&h=400&q=80',
    address: 'Dhanmondi, Dhaka',
    phone: '+880 1234-567909',
    rating: 4.7,
    openHours: '24/7',
    division: 'Dhaka',
    district: 'Dhaka',
    city: 'Dhaka City',
    features: ['ICU', 'Emergency', 'Pharmacy'],
    doctorCount: 100,
    bedCount: 200,
    emergencyService: true,
    ambulanceService: true,
    specialties: ['Internal Medicine', 'Surgery', 'Pediatrics'],
    establishedYear: 1993,
    type: 'private'
  }
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
  'Dhaka': ['Dhaka', 'Gazipur', 'Narayanganj', 'Tangail'],
  'Chittagong': ['Chittagong', 'Cox\'s Bazar', 'Comilla', 'Chandpur']
};

const cities = {
  'Dhaka': ['Dhaka City', 'Gazipur City', 'Narayanganj City'],
  'Chittagong': ['Chittagong City', 'Cox\'s Bazar City', 'Comilla City']
};

export function HospitalListPage() {
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedDivision, setSelectedDivision] = useState('');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [selectedCity, setSelectedCity] = useState('');
  const [selectedType, setSelectedType] = useState<'all' | 'government' | 'private'>('all');

  const handleDivisionChange = (division: string) => {
    setSelectedDivision(division);
    setSelectedDistrict('');
    setSelectedCity('');
  };

  const handleDistrictChange = (district: string) => {
    setSelectedDistrict(district);
    setSelectedCity('');
  };

  const filteredHospitals = hospitals.filter(hospital => {
    const matchesSearch = hospital.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      hospital.address.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesDivision = !selectedDivision || hospital.division === selectedDivision;
    const matchesDistrict = !selectedDistrict || hospital.district === selectedDistrict;
    const matchesCity = !selectedCity || hospital.city === selectedCity;
    const matchesType = selectedType === 'all' || hospital.type === selectedType;

    return matchesSearch && matchesDivision && matchesDistrict && matchesCity && matchesType;
  });

  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-white">
      {/* Hero Section */}
      <div className="relative h-[300px] overflow-hidden">
        <img
          src="https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?auto=format&fit=crop&w=2000&q=80"
          alt="Hospitals"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-blue-900/90 to-blue-800/75">
          <div className="container mx-auto px-4 h-full flex items-center">
            <div className="max-w-2xl">
              <Link to="/appointment" className="flex items-center text-white/80 hover:text-white mb-6">
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back
              </Link>
              <h1 className="text-4xl font-bold text-white mb-4">Find Hospitals</h1>
              <p className="text-xl text-white/90">
                Discover top-rated hospitals and medical centers near you
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-4 -mt-16 relative z-10">
        {/* Search & Filter Card */}
        <div className="bg-white rounded-2xl shadow-lg p-6 mb-12">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Search hospitals by name or location..."
                className="w-full pl-12 pr-4 py-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedType}
              onChange={(e) => setSelectedType(e.target.value as 'all' | 'government' | 'private')}
            >
              <option value="all">All Hospital Types</option>
              <option value="government">Government Hospitals</option>
              <option value="private">Private Hospitals</option>
            </select>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedDivision}
              onChange={(e) => handleDivisionChange(e.target.value)}
            >
              <option value="">All Divisions</option>
              {divisions.map((division) => (
                <option key={division} value={division}>{division}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedDistrict}
              onChange={(e) => handleDistrictChange(e.target.value)}
              disabled={!selectedDivision}
            >
              <option value="">All Districts</option>
              {selectedDivision && districts[selectedDivision as keyof typeof districts].map((district) => (
                <option key={district} value={district}>{district}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedCity}
              onChange={(e) => setSelectedCity(e.target.value)}
              disabled={!selectedDistrict}
            >
              <option value="">All Cities</option>
              {selectedDivision && cities[selectedDivision as keyof typeof cities]?.map((city) => (
                <option key={city} value={city}>{city}</option>
              ))}
            </select>
          </div>
        </div>

        {/* Statistics */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-12">
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-blue-50 p-3 rounded-xl">
                <Building2 className="text-blue-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-blue-600">150+</div>
                <div className="text-gray-600">Hospitals</div>
              </div>
            </div>
          </div>
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-green-50 p-3 rounded-xl">
                <Users className="text-green-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-green-600">2000+</div>
                <div className="text-gray-600">Doctors</div>
              </div>
            </div>
          </div>
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-purple-50 p-3 rounded-xl">
                <Stethoscope className="text-purple-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-purple-600">50+</div>
                <div className="text-gray-600">Specialties</div>
              </div>
            </div>
          </div>
          <div className="bg-white rounded-xl p-6 shadow-sm">
            <div className="flex items-center gap-4">
              <div className="bg-red-50 p-3 rounded-xl">
                <Heart className="text-red-500" size={24} />
              </div>
              <div>
                <div className="text-2xl font-bold text-red-600">24/7</div>
                <div className="text-gray-600">Emergency</div>
              </div>
            </div>
          </div>
        </div>

        {/* Hospital List */}
        <div className="space-y-6">
          {filteredHospitals.length === 0 ? (
            <div className="text-center py-12 bg-white rounded-xl shadow-sm">
              <Building2 size={48} className="text-gray-400 mx-auto mb-4" />
              <p className="text-gray-500 mb-2">No hospitals found matching your criteria</p>
              <button
                onClick={() => {
                  setSearchTerm('');
                  setSelectedDivision('');
                  setSelectedDistrict('');
                  setSelectedCity('');
                  setSelectedType('all');
                }}
                className="text-blue-500 hover:text-blue-600"
              >
                Clear all filters
              </button>
            </div>
          ) : (
            filteredHospitals.map((hospital) => (
              <div 
                key={hospital.id}
                className="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition-all cursor-pointer"
                onClick={() => navigate(`/hospitals/${hospital.id}/departments`)}
              >
                <div className="flex flex-col md:flex-row">
                  <div className="relative w-full md:w-72 h-48">
                    <img
                      src={hospital.image}
                      alt={hospital.name}
                      className="w-full h-full object-cover"
                    />
                    <div className="absolute top-4 right-4">
                      <span className={`px-3 py-1 rounded-full text-sm ${
                        hospital.type === 'government'
                          ? 'bg-green-100 text-green-600'
                          : 'bg-blue-100 text-blue-600'
                      }`}>
                        {hospital.type.charAt(0).toUpperCase() + hospital.type.slice(1)}
                      </span>
                    </div>
                  </div>
                  <div className="flex-grow p-6">
                    <div className="flex justify-between items-start mb-4">
                      <div>
                        <h3 className="text-xl font-semibold mb-2">{hospital.name}</h3>
                        <div className="flex items-center gap-2 text-gray-600 mb-1">
                          <MapPin size={18} />
                          <span>{hospital.address}</span>
                        </div>
                        <div className="flex items-center gap-2 text-gray-600">
                          <Phone size={18} />
                          <span>{hospital.phone}</span>
                        </div>
                      </div>
                      <div className="flex items-center gap-2 bg-yellow-50 px-3 py-1 rounded-full">
                        <Star className="text-yellow-500" size={18} fill="currentColor" />
                        <span className="font-semibold">{hospital.rating}</span>
                      </div>
                    </div>

                    <div className="grid grid-cols-3 gap-4 mb-4">
                      <div className="text-center p-2 bg-gray-50 rounded-lg">
                        <div className="font-semibold text-blue-600">{hospital.doctorCount}+</div>
                        <div className="text-sm text-gray-600">Doctors</div>
                      </div>
                      <div className="text-center p-2 bg-gray-50 rounded-lg">
                        <div className="font-semibold text-green-600">{hospital.bedCount}</div>
                        <div className="text-sm text-gray-600">Beds</div>
                      </div>
                      <div className="text-center p-2 bg-gray-50 rounded-lg">
                        <div className="font-semibold text-purple-600">{hospital.establishedYear}</div>
                        <div className="text-sm text-gray-600">Established</div>
                      </div>
                    </div>

                    <div className="flex flex-wrap gap-2 mb-4">
                      {hospital.specialties.map((specialty, index) => (
                        <span
                          key={index}
                          className="px-3 py-1 bg-blue-50 text-blue-600 rounded-full text-sm"
                        >
                          {specialty}
                        </span>
                      ))}
                    </div>

                    <div className="flex items-center justify-between">
                      <div className="flex gap-4">
                        {hospital.emergencyService && (
                          <span className="flex items-center gap-1 text-red-500">
                            <Heart size={18} />
                            24/7 Emergency
                          </span>
                        )}
                        {hospital.ambulanceService && (
                          <span className="flex items-center gap-1 text-green-500">
                            <Ambulance size={18} />
                            Ambulance
                          </span>
                        )}
                      </div>
                      <button className="px-6 py-2 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors">
                        View Departments
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}