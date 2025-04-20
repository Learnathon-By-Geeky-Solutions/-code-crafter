import { useState } from 'react';
import { ArrowLeft, Search, MapPin, Phone, Clock, Heart, Share2, Filter, AlertCircle, Droplet } from 'lucide-react';
import { Link } from 'react-router-dom';

interface BloodPost {
  id: string;
  title: string;
  bloodGroup: string;
  requiredUnits: number;
  location: {
    hospital: string;
    division: string;
    district: string;
    city: string;
  };
  contactNumber: string;
  requiredDate: string;
  requiredTime: string;
  urgency: 'Urgent' | 'Emergency' | 'Normal';
  author: {
    name: string;
    avatar: string;
  };
  createdAt: string;
  status: 'Active' | 'Fulfilled' | 'Expired';
  responses: number;
  shares: number;
  description: string;
  image: string;
}

const bloodPosts: BloodPost[] = [
  {
    id: '1',
    title: 'Need A+ blood in Mirpur 10',
    bloodGroup: 'A+',
    requiredUnits: 2,
    location: {
      hospital: 'Popular Hospital',
      division: 'Dhaka',
      district: 'Dhaka',
      city: 'Mirpur'
    },
    contactNumber: '+880 1234-567890',
    requiredDate: '2024-03-20',
    requiredTime: '09:00 AM',
    urgency: 'Urgent',
    author: {
      name: 'Kabir Ahmed',
      avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80'
    },
    createdAt: '2024-03-19T10:00:00Z',
    status: 'Active',
    responses: 3,
    shares: 12,
    description: 'Urgently need 2 bags of A+ blood for my father who is undergoing surgery at Popular Hospital, Mirpur 10. The surgery is scheduled for tomorrow morning at 9 AM.',
    image: 'https://images.unsplash.com/photo-1615461066841-6116e61058f4?auto=format&fit=crop&w=800&h=400&q=80'
  },
  {
    id: '2',
    title: 'Emergency B- blood needed',
    bloodGroup: 'B-',
    requiredUnits: 1,
    location: {
      hospital: 'Square Hospital',
      division: 'Dhaka',
      district: 'Dhaka',
      city: 'Panthapath'
    },
    contactNumber: '+880 1234-567891',
    requiredDate: '2024-03-19',
    requiredTime: '14:00 PM',
    urgency: 'Emergency',
    author: {
      name: 'Sarah Khan',
      avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80'
    },
    createdAt: '2024-03-19T08:00:00Z',
    status: 'Active',
    responses: 2,
    shares: 8,
    description: 'Emergency requirement of B- blood for accident victim. Need blood within next 2 hours.',
    image: 'https://images.unsplash.com/photo-1579165466741-7f35e4755660?auto=format&fit=crop&w=800&h=400&q=80'
  },
  {
    id: '3',
    title: 'O+ blood needed for surgery',
    bloodGroup: 'O+',
    requiredUnits: 3,
    location: {
      hospital: 'United Hospital',
      division: 'Dhaka',
      district: 'Dhaka',
      city: 'Gulshan'
    },
    contactNumber: '+880 1234-567892',
    requiredDate: '2024-03-21',
    requiredTime: '11:00 AM',
    urgency: 'Normal',
    author: {
      name: 'Rahim Mia',
      avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=150&h=150&q=80'
    },
    createdAt: '2024-03-18T15:30:00Z',
    status: 'Active',
    responses: 5,
    shares: 15,
    description: 'Need O+ blood for scheduled heart surgery. Please help if you can donate.',
    image: 'https://images.unsplash.com/photo-1536856136534-bb679c52a9aa?auto=format&fit=crop&w=800&h=400&q=80'
  },
  {
    id: '4',
    title: 'Emergency AB- blood requirement',
    bloodGroup: 'AB-',
    requiredUnits: 2,
    location: {
      hospital: 'Apollo Hospital',
      division: 'Dhaka',
      district: 'Dhaka',
      city: 'Bashundhara'
    },
    contactNumber: '+880 1234-567893',
    requiredDate: '2024-03-19',
    requiredTime: '16:00 PM',
    urgency: 'Emergency',
    author: {
      name: 'Fatima Khan',
      avatar: 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=150&h=150&q=80'
    },
    createdAt: '2024-03-19T09:15:00Z',
    status: 'Active',
    responses: 1,
    shares: 20,
    description: 'Urgent requirement of AB- blood for accident victim. Critical condition.',
    image: 'https://images.unsplash.com/photo-1631217868264-e5b90bb7e133?auto=format&fit=crop&w=800&h=400&q=80'
  },
  {
    id: '5',
    title: 'Need B+ blood for cancer patient',
    bloodGroup: 'B+',
    requiredUnits: 4,
    location: {
      hospital: 'Evercare Hospital',
      division: 'Dhaka',
      district: 'Dhaka',
      city: 'Bashundhara'
    },
    contactNumber: '+880 1234-567894',
    requiredDate: '2024-03-22',
    requiredTime: '09:30 AM',
    urgency: 'Urgent',
    author: {
      name: 'Mohammad Ali',
      avatar: 'https://images.unsplash.com/photo-1566492031773-4f4e44671857?auto=format&fit=crop&w=150&h=150&q=80'
    },
    createdAt: '2024-03-19T07:00:00Z',
    status: 'Active',
    responses: 2,
    shares: 25,
    description: 'Need B+ blood for ongoing cancer treatment. Regular transfusion required.',
    image: 'https://images.unsplash.com/photo-1584982751601-97dcc096659c?auto=format&fit=crop&w=800&h=400&q=80'
  },
  {
    id: '6',
    title: 'A- blood needed for childbirth',
    bloodGroup: 'A-',
    requiredUnits: 2,
    location: {
      hospital: 'Square Hospital',
      division: 'Dhaka',
      district: 'Dhaka',
      city: 'Panthapath'
    },
    contactNumber: '+880 1234-567895',
    requiredDate: '2024-03-20',
    requiredTime: '13:00 PM',
    urgency: 'Urgent',
    author: {
      name: 'Nusrat Jahan',
      avatar: 'https://images.unsplash.com/photo-1594824476967-48c8b964273f?auto=format&fit=crop&w=150&h=150&q=80'
    },
    createdAt: '2024-03-19T06:30:00Z',
    status: 'Active',
    responses: 3,
    shares: 18,
    description: 'Need A- blood for scheduled C-section delivery tomorrow.',
    image: 'https://images.unsplash.com/photo-1584515933487-779824d29309?auto=format&fit=crop&w=800&h=400&q=80'
  },
  {
    id: '7',
    title: 'O- blood for kidney transplant',
    bloodGroup: 'O-',
    requiredUnits: 3,
    location: {
      hospital: 'Labaid Hospital',
      division: 'Dhaka',
      district: 'Dhaka',
      city: 'Dhanmondi'
    },
    contactNumber: '+880 1234-567896',
    requiredDate: '2024-03-23',
    requiredTime: '08:00 AM',
    urgency: 'Normal',
    author: {
      name: 'Kamal Hassan',
      avatar: 'https://images.unsplash.com/photo-1566492031773-4f4e44671857?auto=format&fit=crop&w=150&h=150&q=80'
    },
    createdAt: '2024-03-19T05:45:00Z',
    status: 'Active',
    responses: 4,
    shares: 12,
    description: 'Need O- blood for upcoming kidney transplant surgery.',
    image: 'https://images.unsplash.com/photo-1579684385127-1ef15d508118?auto=format&fit=crop&w=800&h=400&q=80'
  },
  {
    id: '8',
    title: 'AB+ blood for heart surgery',
    bloodGroup: 'AB+',
    requiredUnits: 2,
    location: {
      hospital: 'Ibn Sina Hospital',
      division: 'Dhaka',
      district: 'Dhaka',
      city: 'Dhanmondi'
    },
    contactNumber: '+880 1234-567897',
    requiredDate: '2024-03-21',
    requiredTime: '10:30 AM',
    urgency: 'Urgent',
    author: {
      name: 'Aisha Rahman',
      avatar: 'https://images.unsplash.com/photo-1594824476967-48c8b964273f?auto=format&fit=crop&w=150&h=150&q=80'
    },
    createdAt: '2024-03-19T04:20:00Z',
    status: 'Active',
    responses: 2,
    shares: 15,
    description: 'Need AB+ blood for emergency heart surgery.',
    image: 'https://images.unsplash.com/photo-1628348068343-c6a848d2b6dd?auto=format&fit=crop&w=800&h=400&q=80'
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
  'Dhaka': ['Dhaka', 'Gazipur', 'Narayanganj'],
  'Chittagong': ['Chittagong', "Cox's Bazar", 'Comilla']
};

const cities = {
  'Dhaka': ['Mirpur', 'Gulshan', 'Dhanmondi', 'Uttara'],
  'Gazipur': ['Gazipur Sadar', 'Tongi', 'Sreepur']
};

const bloodGroups = ['A+', 'A-', 'B+', 'B-', 'O+', 'O-', 'AB+', 'AB-'];

export function BloodDonationPostsPage() {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedBloodGroup, setSelectedBloodGroup] = useState('');
  const [selectedDivision, setSelectedDivision] = useState('');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [selectedCity, setSelectedCity] = useState('');
  const [selectedUrgency, setSelectedUrgency] = useState<'all' | 'Urgent' | 'Emergency' | 'Normal'>('all');

  const handleDivisionChange = (division: string) => {
    setSelectedDivision(division);
    setSelectedDistrict('');
    setSelectedCity('');
  };

  const handleDistrictChange = (district: string) => {
    setSelectedDistrict(district);
    setSelectedCity('');
  };

  const filteredPosts = bloodPosts.filter(post => {
    const matchesSearch = 
      post.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      post.location.hospital.toLowerCase().includes(searchTerm.toLowerCase()) ||
      post.description.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesBloodGroup = !selectedBloodGroup || post.bloodGroup === selectedBloodGroup;
    const matchesDivision = !selectedDivision || post.location.division === selectedDivision;
    const matchesDistrict = !selectedDistrict || post.location.district === selectedDistrict;
    const matchesCity = !selectedCity || post.location.city === selectedCity;
    const matchesUrgency = selectedUrgency === 'all' || post.urgency === selectedUrgency;

    return matchesSearch && matchesBloodGroup && matchesDivision && matchesDistrict && matchesCity && matchesUrgency;
  });

  return (
    <div className="min-h-screen bg-gradient-to-b from-red-50 to-white">
      {/* Hero Section */}
      <div className="relative h-[300px] overflow-hidden">
        <img
          src="https://images.unsplash.com/photo-1615461066841-6116e61058f4?auto=format&fit=crop&w=2000&q=80"
          alt="Blood Donation"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-red-900/90 to-red-800/75">
          <div className="container mx-auto px-4 h-full flex items-center">
            <div className="max-w-2xl">
              <Link to="/blood-donation" className="flex items-center text-white/80 hover:text-white mb-6">
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back to Blood Donation
              </Link>
              <h1 className="text-4xl font-bold text-white mb-4">Blood Donation Posts</h1>
              <p className="text-xl text-white/90">
                Find and respond to blood donation requests in your area
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
                placeholder="Search blood requests..."
                className="w-full pl-12 pr-4 py-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="flex gap-4">
              <select
                className="flex-1 p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
                value={selectedBloodGroup}
                onChange={(e) => setSelectedBloodGroup(e.target.value)}
              >
                <option value="">All Blood Groups</option>
                {bloodGroups.map(group => (
                  <option key={group} value={group}>{group}</option>
                ))}
              </select>
              <select
                className="flex-1 p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
                value={selectedUrgency}
                onChange={(e) => setSelectedUrgency(e.target.value as 'all' | 'Urgent' | 'Emergency' | 'Normal')}
              >
                <option value="all">All Urgency</option>
                <option value="Emergency">Emergency</option>
                <option value="Urgent">Urgent</option>
                <option value="Normal">Normal</option>
              </select>
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedDivision}
              onChange={(e) => handleDivisionChange(e.target.value)}
            >
              <option value="">All Divisions</option>
              {divisions.map(division => (
                <option key={division} value={division}>{division}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedDistrict}
              onChange={(e) => handleDistrictChange(e.target.value)}
              disabled={!selectedDivision}
            >
              <option value="">All Districts</option>
              {selectedDivision && districts[selectedDivision as keyof typeof districts]?.map(district => (
                <option key={district} value={district}>{district}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedCity}
              onChange={(e) => setSelectedCity(e.target.value)}
              disabled={!selectedDistrict}
            >
              <option value="">All Cities</option>
              {selectedDistrict && cities[selectedDistrict as keyof typeof cities]?.map(city => (
                <option key={city} value={city}>{city}</option>
              ))}
            </select>
          </div>
        </div>

        {/* Blood Posts */}
        <div className="space-y-6">
          {filteredPosts.length === 0 ? (
            <div className="text-center py-12 bg-white rounded-xl shadow-sm">
              <Droplet size={48} className="text-red-400 mx-auto mb-4" />
              <p className="text-gray-500 mb-2">No blood requests found matching your criteria</p>
              <button
                onClick={() => {
                  setSearchTerm('');
                  setSelectedBloodGroup('');
                  setSelectedDivision('');
                  setSelectedDistrict('');
                  setSelectedCity('');
                  setSelectedUrgency('all');
                }}
                className="text-red-500 hover:text-red-600"
              >
                Clear all filters
              </button>
            </div>
          ) : (
            filteredPosts.map((post) => (
              <Link
                key={post.id}
                to={`/blood-donation/post/${post.id}`}
                className="block bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition-all"
              >
                <div className="flex flex-col md:flex-row">
                  <div className="relative w-full md:w-72 h-48">
                    <img
                      src={post.image}
                      alt={post.title}
                      className="w-full h-full object-cover"
                    />
                    <div className="absolute top-4 right-4">
                      <span className={`px-3 py-1 rounded-full text-sm ${
                        post.urgency === 'Emergency'
                          ? 'bg-red-500 text-white'
                          : post.urgency === 'Urgent'
                          ? 'bg-orange-500 text-white'
                          : 'bg-green-500 text-white'
                      }`}>
                        {post.urgency}
                      </span>
                    </div>
                  </div>
                  <div className="flex-grow p-6">
                    <div className="flex items-center gap-4 mb-4">
                      <img
                        src={post.author.avatar}
                        alt={post.author.name}
                        className="w-10 h-10 rounded-full object-cover"
                      />
                      <div>
                        <h3 className="font-medium">{post.author.name}</h3>
                        <p className="text-sm text-gray-500">
                          {new Date(post.createdAt).toLocaleDateString()}
                        </p>
                      </div>
                    </div>

                    <h2 className="text-xl font-semibold mb-4">{post.title}</h2>

                    <div className="grid grid-cols-2 gap-4 mb-4">
                      <div className="bg-red-50 p-3 rounded-lg">
                        <div className="font-semibold text-red-600">{post.bloodGroup}</div>
                        <div className="text-sm text-gray-600">{post.requiredUnits} units needed</div>
                      </div>
                      <div className="bg-blue-50 p-3 rounded-lg">
                        <div className="font-semibold text-blue-600">{post.requiredTime}</div>
                        <div className="text-sm text-gray-600">{new Date(post.requiredDate).toLocaleDateString()}</div>
                      </div>
                    </div>

                    <div className="space-y-2 text-sm text-gray-600 mb-4">
                      <div className="flex items-center gap-2">
                        <MapPin size={16} />
                        <span>{post.location.hospital}, {post.location.city}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Phone size={16} />
                        <span>{post.contactNumber}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Clock size={16} />
                        <span>{post.status}</span>
                      </div>
                    </div>

                    <div className="flex items-center justify-between pt-4 border-t">
                      <div className="flex items-center gap-6">
                        <span className="flex items-center gap-1 text-gray-600">
                          <Heart size={16} />
                          {post.responses} Responses
                        </span>
                        <span className="flex items-center gap-1 text-gray-600">
                          <Share2 size={16} />
                          {post.shares} Shares
                        </span>
                      </div>
                      {post.status === 'Active' && (
                        <div className="flex items-center gap-2 text-green-500">
                          <AlertCircle size={16} />
                          Active Request
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              </Link>
            ))
          )}
        </div>
      </div>
    </div>
  );
}