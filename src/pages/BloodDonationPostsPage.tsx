import { useEffect, useState } from 'react';
import { ArrowLeft, Search, MapPin, Phone, Clock, Heart, Share2, AlertCircle, Droplet } from 'lucide-react';
import { Link } from 'react-router-dom';
import { api } from '../lib/api';

interface BloodPost {
  id: number;
  patientName: string;
  bloodType: string;
  quantity: number;
  hospitalName: string;
  contactNumber: string;
  description: string;
  date: string;
  upazila: {
    name: string;
    district: {
      name: string;
      division: {
        name: string;
      };
    };
  };
  bloodRequestPostCommentResponses: {
    userFirstName: string;
    userLastName: string;
    content: string;
  }[];
}

export function BloodDonationPostsPage() {
  const [posts, setPosts] = useState<BloodPost[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedBloodGroup, setSelectedBloodGroup] = useState('');
  const [selectedDivision, setSelectedDivision] = useState('');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [selectedCity, setSelectedCity] = useState('');

  useEffect(() => {
    fetchPosts();
  }, []);

  const fetchPosts = async () => {
    try {
      const { data: baseResponse } = await api.get('/api/v1/blood/blood-request-post-page');
      if (baseResponse.code === 'XS0001') {
        setPosts(baseResponse.data.content);
      }
    } catch (error) {
      console.error('Error fetching posts:', error);
    }
  };

  const bloodGroups = ['A_POS', 'A_NEG', 'B_POS', 'B_NEG', 'AB_POS', 'AB_NEG', 'O_POS', 'O_NEG'];

  const handleDivisionChange = (division: string) => {
    setSelectedDivision(division);
    setSelectedDistrict('');
    setSelectedCity('');
  };

  const handleDistrictChange = (district: string) => {
    setSelectedDistrict(district);
    setSelectedCity('');
  };

  const filteredPosts = posts.filter(post => {
    const matchesSearch =
      post.patientName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      post.hospitalName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      post.description.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesBloodGroup = !selectedBloodGroup || post.bloodType === selectedBloodGroup;
    const matchesDivision = !selectedDivision || post.upazila.district.division.name === selectedDivision;
    const matchesDistrict = !selectedDistrict || post.upazila.district.name === selectedDistrict;
    const matchesCity = !selectedCity || post.upazila.name === selectedCity;

    return matchesSearch && matchesBloodGroup && matchesDivision && matchesDistrict && matchesCity;
  });

  return (
    <div className="min-h-screen bg-gradient-to-b from-red-50 to-white">
      {/* Hero */}
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
              <p className="text-xl text-white/90">Find and respond to blood donation requests in your area</p>
            </div>
          </div>
        </div>
      </div>

      {/* Filters */}
      <div className="container mx-auto px-4 -mt-16 pb-16 relative z-10">
        <div className="bg-white rounded-2xl shadow-lg p-6 mb-12 space-y-6">
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

          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedBloodGroup}
              onChange={(e) => setSelectedBloodGroup(e.target.value)}
            >
              <option value="">All Blood Groups</option>
              {bloodGroups.map(group => (
                <option key={group} value={group}>{group.replace('_POS', '+').replace('_NEG', '-')}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-xl bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedDivision}
              onChange={(e) => handleDivisionChange(e.target.value)}
            >
              <option value="">All Divisions</option>
              {[...new Set(posts.map(post => post.upazila.district.division.name))].map(division => (
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
              {[...new Set(posts.filter(post => post.upazila.district.division.name === selectedDivision).map(post => post.upazila.district.name))].map(district => (
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
              {[...new Set(posts.filter(post => post.upazila.district.name === selectedDistrict).map(post => post.upazila.name))].map(city => (
                <option key={city} value={city}>{city}</option>
              ))}
            </select>
          </div>
        </div>

        {/* Blood Posts List */}
        <div className="space-y-6">
          {filteredPosts.length === 0 ? (
            <div className="text-center py-12 bg-white rounded-xl shadow-sm">
              <Droplet size={48} className="text-red-400 mx-auto mb-4" />
              <p className="text-gray-500 mb-2">No blood requests found</p>
              <button
                onClick={() => {
                  setSearchTerm('');
                  setSelectedBloodGroup('');
                  setSelectedDivision('');
                  setSelectedDistrict('');
                  setSelectedCity('');
                }}
                className="text-red-500 hover:text-red-600"
              >
                Clear Filters
              </button>
            </div>
          ) : (
            filteredPosts.map(post => (
              <div
                key={post.id}
                className="block bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition-all"
              >
                <div className="flex flex-col md:flex-row">
                  {/* Image Side */}
                  <div className="relative w-full md:w-72 h-48">
                    <img
                      src="https://images.unsplash.com/photo-1615461066841-6116e61058f4?auto=format&fit=crop&w=800&q=80"
                      alt={post.patientName}
                      className="w-full h-full object-cover"
                    />
                  </div>

                  {/* Content Side */}
                  <div className="flex-grow p-6 flex flex-col justify-between">
                    <div>
                      <div className="flex items-center gap-4 mb-4">
                        <img
                          src="https://images.unsplash.com/photo-1607746882042-944635dfe10e?auto=format&fit=crop&w=150&h=150&q=80"
                          alt="Avatar"
                          className="w-10 h-10 rounded-full object-cover"
                        />
                        <div>
                          <h3 className="font-medium">{post.patientName}</h3>
                          <p className="text-sm text-gray-500">
                            {new Date(post.date).toLocaleDateString()}
                          </p>
                        </div>
                      </div>

                      <h2 className="text-xl font-semibold mb-4">{post.bloodType.replace('_POS', '+').replace('_NEG', '-')} blood needed</h2>

                      <div className="grid grid-cols-2 gap-4 mb-4">
                        <div className="bg-red-50 p-3 rounded-lg">
                          <div className="font-semibold text-red-600">{post.bloodType.replace('_POS', '+').replace('_NEG', '-')}</div>
                          <div className="text-sm text-gray-600">{post.quantity} units</div>
                        </div>
                        <div className="bg-blue-50 p-3 rounded-lg">
                          <div className="font-semibold text-blue-600">{post.hospitalName}</div>
                          <div className="text-sm text-gray-600">{post.upazila.name}, {post.upazila.district.name}</div>
                        </div>
                      </div>

                      <div className="space-y-2 text-sm text-gray-600 mb-4">
                        <div className="flex items-center gap-2">
                          <MapPin size={16} />
                          <span>{post.hospitalName}</span>
                        </div>
                        <div className="flex items-center gap-2">
                          <Phone size={16} />
                          <span>{post.contactNumber}</span>
                        </div>
                        <div className="flex items-center gap-2">
                          <Clock size={16} />
                          <span>Requested for: {new Date(post.date).toLocaleDateString()}</span>
                        </div>
                      </div>
                    </div>

                    {/* View Details Button */}
                    <div className="mt-4 flex justify-end">
                      <Link
                        to={`/blood-donation/post/${post.id}`}
                        className="px-4 py-2 bg-blue-500 hover:bg-blue-600 active:scale-95 text-white rounded-lg font-semibold text-sm transition-all"
                      >
                        View Details
                      </Link>
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
