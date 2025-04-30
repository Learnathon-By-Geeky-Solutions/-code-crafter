import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Search, MapPin, Phone, Star, Clock, Filter } from 'lucide-react';

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
  departments: string[];
  doctors: number;
  established: number;
  emergencyService: boolean;
  ambulanceService: boolean;
  type: 'government' | 'private';
}

const generateHospitals = (): Hospital[] => {
  const features = [
    '24/7 Emergency',
    'ICU Facilities',
    'Diagnostic Center',
    'Pharmacy',
    'Blood Bank',
    'Operation Theater',
    'Ambulance Service',
    'Cafeteria',
    'Parking',
    'ATM Booth'
  ];

  const departments = [
    'Cardiology',
    'Neurology',
    'Orthopedics',
    'Gynecology',
    'Pediatrics',
    'Oncology',
    'Dermatology',
    'ENT',
    'Dental',
    'Psychiatry'
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

  const hospitals: Hospital[] = [];
  const hospitalNames = [
    'City General Hospital',
    'Central Medical Center',
    'Metropolitan Hospital',
    'United Hospital',
    'Square Hospital',
    'Apollo Hospital',
    'Popular Medical Center',
    'Evercare Hospital',
    'Green Life Hospital',
    'Ibn Sina Hospital'
  ];

  for (let i = 0; i < 50; i++) {
    const division = divisions[Math.floor(Math.random() * divisions.length)];
    const district = districts[division as keyof typeof districts][Math.floor(Math.random() * districts[division as keyof typeof districts].length)];
    const city = cities[district as keyof typeof cities]?.[Math.floor(Math.random() * (cities[district as keyof typeof cities]?.length || 1))] || district;

    const randomFeatures = [...features]
      .sort(() => Math.random() - 0.5)
      .slice(0, Math.floor(Math.random() * 5) + 3);

    const randomDepartments = [...departments]
      .sort(() => Math.random() - 0.5)
      .slice(0, Math.floor(Math.random() * 5) + 3);

    const baseHospitalName = hospitalNames[i % hospitalNames.length];
    const hospitalName = i < hospitalNames.length 
      ? baseHospitalName 
      : `${baseHospitalName} ${Math.floor(i / hospitalNames.length) + 1}`;

    hospitals.push({
      id: `hosp-${i + 1}`,
      name: hospitalName,
      image: 'https://images.unsplash.com/photo-1587351021759-3e566b6af7cc?auto=format&fit=crop&w=800&q=80',
      address: `${Math.floor(Math.random() * 200) + 1}, ${city}, ${district}`,
      phone: `+880 ${Math.floor(Math.random() * 2) + 1}${Math.random().toString().slice(2, 11)}`,
      rating: Number((Math.random() * (5 - 3.5) + 3.5).toFixed(1)),
      openHours: '24/7',
      division,
      district,
      city,
      features: randomFeatures,
      departments: randomDepartments,
      doctors: Math.floor(Math.random() * 100) + 20,
      established: Math.floor(Math.random() * (2023 - 1990) + 1990),
      emergencyService: Math.random() > 0.2,
      ambulanceService: Math.random() > 0.3,
      type: Math.random() > 0.7 ? 'government' : 'private'
    });
  }

  return hospitals;
};

const hospitals = generateHospitals();

export function FindHospitalsPage() {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedLocation, setSelectedLocation] = useState({
    division: '',
    district: '',
    city: ''
  });
  const [selectedDepartment, setSelectedDepartment] = useState('');
  const [selectedType, setSelectedType] = useState<'all' | 'government' | 'private'>('all');

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

  const departments = [
    'Cardiology',
    'Neurology',
    'Orthopedics',
    'Gynecology',
    'Pediatrics',
    'Oncology',
    'Dermatology',
    'ENT',
    'Dental',
    'Psychiatry'
  ];

  const filteredHospitals = hospitals.filter(hospital => {
    const matchesSearch = 
      hospital.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      hospital.address.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesLocation = (
      (!selectedLocation.division || hospital.division === selectedLocation.division) &&
      (!selectedLocation.district || hospital.district === selectedLocation.district) &&
      (!selectedLocation.city || hospital.city === selectedLocation.city)
    );

    const matchesDepartment = !selectedDepartment || hospital.departments.includes(selectedDepartment);
    const matchesType = selectedType === 'all' || hospital.type === selectedType;

    return matchesSearch && matchesLocation && matchesDepartment && matchesType;
  });

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">Find Hospitals</h1>
          <p className="text-gray-600 max-w-2xl mx-auto">
            Discover and connect with the best hospitals in your area. Get detailed information about facilities, departments, and available services.
          </p>
        </div>

        <div className="bg-white rounded-xl shadow-lg p-6 mb-12">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Search hospitals by name or location..."
                className="w-full pl-12 pr-4 py-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedDepartment}
              onChange={(e) => setSelectedDepartment(e.target.value)}
            >
              <option value="">All Departments</option>
              {departments.map(department => (
                <option key={department} value={department}>{department}</option>
              ))}
            </select>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
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

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
              value={selectedType}
              onChange={(e) => setSelectedType(e.target.value as 'all' | 'government' | 'private')}
            >
              <option value="all">All Types</option>
              <option value="government">Government</option>
              <option value="private">Private</option>
            </select>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredHospitals.map((hospital) => (
            <Link
              key={hospital.id}
              to={`/hospitals/${hospital.id}/departments`}
              className="bg-white rounded-lg shadow-sm overflow-hidden hover:shadow-md transition-shadow"
            >
              <img
                src={hospital.image}
                alt={hospital.name}
                className="w-full h-48 object-cover"
              />
              <div className="p-6">
                <div className="flex items-center justify-between mb-2">
                  <h3 className="font-semibold text-lg">{hospital.name}</h3>
                  <div className="flex items-center gap-1">
                    <Star className="w-5 h-5 text-yellow-500 fill-current" />
                    <span>{hospital.rating}</span>
                  </div>
                </div>

                <div className="space-y-2 text-sm text-gray-600 mb-4">
                  <div className="flex items-center gap-2">
                    <MapPin size={16} />
                    <span>{hospital.address}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Phone size={16} />
                    <span>{hospital.phone}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Clock size={16} />
                    <span>{hospital.openHours}</span>
                  </div>
                </div>

                <div className="flex flex-wrap gap-2 mb-4">
                  {hospital.departments.slice(0, 3).map((department, index) => (
                    <span
                      key={index}
                      className="px-2 py-1 bg-blue-50 text-blue-600 rounded-full text-xs"
                    >
                      {department}
                    </span>
                  ))}
                  {hospital.departments.length > 3 && (
                    <span className="px-2 py-1 bg-gray-50 text-gray-600 rounded-full text-xs">
                      +{hospital.departments.length - 3} more
                    </span>
                  )}
                </div>

                <div className="flex items-center justify-between text-sm">
                  <span className="text-gray-600">{hospital.doctors} Doctors</span>
                  <span className={`px-2 py-1 rounded-full ${
                    hospital.type === 'government'
                      ? 'bg-green-100 text-green-600'
                      : 'bg-blue-100 text-blue-600'
                  }`}>
                    {hospital.type.charAt(0).toUpperCase() + hospital.type.slice(1)}
                  </span>
                </div>

                {(hospital.emergencyService || hospital.ambulanceService) && (
                  <div className="mt-4 flex gap-2">
                    {hospital.emergencyService && (
                      <span className="px-2 py-1 bg-red-100 text-red-600 rounded-full text-xs">
                        24/7 Emergency
                      </span>
                    )}
                    {hospital.ambulanceService && (
                      <span className="px-2 py-1 bg-green-100 text-green-600 rounded-full text-xs">
                        Ambulance Available
                      </span>
                    )}
                  </div>
                )}
              </div>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}