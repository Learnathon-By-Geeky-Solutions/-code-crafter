import { useEffect, useState } from 'react';
import { Search, MapPin } from 'lucide-react';
import { api } from '../lib/api';
import { toast } from 'react-hot-toast';

interface Donor {
  id: number;
  fullName: string;
  phone: string;
  bloodType: string;
  upazila: string;
  area: string;
  isAvailable: boolean;
}

interface UserProfile {
  id: number;
  phone: string;
  gender: string | null;
}

const bloodGroups = ['A+', 'A-', 'B+', 'B-', 'O+', 'O-', 'AB+', 'AB-'];

const bloodGroupMap: { [key: string]: string } = {
  'A+': 'A_POS', 'A-': 'A_NEG',
  'B+': 'B_POS', 'B-': 'B_NEG',
  'AB+': 'AB_POS', 'AB-': 'AB_NEG',
  'O+': 'O_POS', 'O-': 'O_NEG'
};

export function BloodDonorsPage() {
  const [donors, setDonors] = useState<Donor[]>([]);
  const [userProfiles, setUserProfiles] = useState<UserProfile[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedBloodGroup, setSelectedBloodGroup] = useState('');
  const [selectedUpazila, setSelectedUpazila] = useState('');
  const [loading, setLoading] = useState(true);
  const maleAvatar = "https://cdn-icons-png.flaticon.com/512/2922/2922510.png";
  const femaleAvatar = "https://cdn-icons-png.flaticon.com/512/2922/2922561.png";
  const unknownAvatar = "https://cdn-icons-png.flaticon.com/512/2922/2922688.png";


  useEffect(() => {
    fetchDonors();
  }, []);

  const fetchDonors = async () => {
    try {
      const { data } = await api.get('/api/v1/donor/available', {
        params: {
          bloodType: selectedBloodGroup ? bloodGroupMap[selectedBloodGroup] : undefined,
          page: 0,
          size: 100,
          sortBy: 'id',
          direction: 'asc'
        }
      });

      if (data?.code === 'XS0001') {
        setDonors(data.data.content);
        fetchUserProfiles(data.data.content.map((donor: Donor) => donor.phone));
      } else {
        toast.error(data?.message || 'Failed to fetch donors.');
      }
    } catch (error) {
      console.error('Error fetching donors:', error);
      toast.error('Error fetching donors.');
    } finally {
      setLoading(false);
    }
  };

  const fetchUserProfiles = async (phones: string[]) => {
    try {
      const results: UserProfile[] = [];

      await Promise.all(
        phones.map(async (phone) => {
          try {
            const res = await api.get('/api/v1/user', { params: { phone } });
            if (res.data?.code === 'XS0001') {
              results.push({
                id: res.data.data.id,
                phone: res.data.data.phone,
                gender: res.data.data.gender,
              });
            }
          } catch (e) {
            console.warn(`User not found for phone: ${phone}`);
          }
        })
      );

      setUserProfiles(results);
    } catch (e) {
      console.error('Error fetching user profiles:', e);
    }
  };

  const getAvatar = (phone: string) => {
    const profile = userProfiles.find((p) => p.phone === phone);
  
    if (!profile || !profile.gender) return unknownAvatar;
  
    const gender = profile.gender.toLowerCase();
    if (gender === 'male') return maleAvatar;
    if (gender === 'female') return femaleAvatar;
  
    return unknownAvatar;
  };
  

  const filteredDonors = donors.filter(donor => {
    const matchesBloodGroup = !selectedBloodGroup || donor.bloodType === bloodGroupMap[selectedBloodGroup];
    const matchesUpazila = !selectedUpazila || donor.upazila === selectedUpazila;
    const matchesSearch = donor.fullName.toLowerCase().includes(searchTerm.toLowerCase());

    return matchesBloodGroup && matchesUpazila && matchesSearch;
  });

  const uniqueUpazilas = Array.from(new Set(donors.map(d => d.upazila)));

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">Blood Donors Directory</h1>
          <p className="text-gray-600 max-w-2xl mx-auto">
            Find blood donors near you. Connect with verified donors and help save lives.
          </p>
        </div>

        {/* Filters */}
        <div className="bg-white rounded-xl shadow-lg p-6 mb-12">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
            <div className="relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Search donors by name..."
                className="w-full pl-12 pr-4 py-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedBloodGroup}
              onChange={(e) => setSelectedBloodGroup(e.target.value)}
            >
              <option value="">All Blood Groups</option>
              {bloodGroups.map(group => (
                <option key={group} value={group}>{group}</option>
              ))}
            </select>

            <select
              className="p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={selectedUpazila}
              onChange={(e) => setSelectedUpazila(e.target.value)}
            >
              <option value="">All Upazilas</option>
              {uniqueUpazilas.map(upazila => (
                <option key={upazila} value={upazila}>{upazila}</option>
              ))}
            </select>
          </div>
        </div>

        {/* Donors List */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {loading ? (
            <div className="text-center text-gray-500 py-12 col-span-full">Loading donors...</div>
          ) : filteredDonors.length === 0 ? (
            <div className="text-center text-gray-500 py-12 col-span-full">No donors found.</div>
          ) : (
            filteredDonors.map((donor) => (
              <div key={donor.id} className="bg-white rounded-lg shadow-sm overflow-hidden hover:shadow-md transition-all">
                <img
                  src={getAvatar(donor.phone)}
                  alt="Donor"
                  className="w-full h-48 object-cover"
                />
                <div className="p-4">
                  <div className="flex items-center justify-between mb-2">
                    <h3 className="font-semibold text-lg">{donor.fullName}</h3>
                    <span className="px-3 py-1 bg-red-100 text-red-600 rounded-full text-sm font-medium">
                      {donor.bloodType.replace('_POS', '+').replace('_NEG', '-')}
                    </span>
                  </div>

                  <div className="space-y-2 text-sm text-gray-600 mb-4">
                    <div className="flex items-center gap-2">
                      <MapPin size={16} />
                      <span>{donor.upazila}</span>
                    </div>
                    <div className="flex items-center gap-2">
                      <span>{donor.area}</span>
                    </div>
                  </div>

                  <div className="flex items-center justify-between">
                    <a
                      href={`tel:${donor.phone}`}
                      className="text-blue-500 hover:text-blue-600 font-medium"
                    >
                      Contact
                    </a>
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
