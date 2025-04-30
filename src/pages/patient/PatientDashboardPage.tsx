// ✅ Final version with full location support + all existing features preserved

import { useEffect, useState } from 'react';
import { toast } from 'react-hot-toast';
import {
  Calendar, Activity, Heart, MapPin, User, Mail, Phone
} from 'lucide-react';
import { Dialog } from '@headlessui/react';
import { api } from '../../lib/api';
import { useLocationStore } from '../../stores/areaLocationStore';
import type { Division, District, Upazila } from '../../stores/areaLocationStore';

interface DonationRecord {
  recipient: string;
  date: string;
  location: string;
  bloodGroup: string;
  units: number;
  certificate?: string;
}

function getFullLocationName(upazilaId: number | null, upazilas: Upazila[], districts: District[], divisions: Division[]): string {
  if (!upazilaId) return 'Location not set';
  const upazila = upazilas.find(u => u.id === upazilaId);
  const district = upazila && districts.find(d => d.id === upazila.district_id);
  const division = district && divisions.find(v => v.id === district.division_id);
  return upazila && district && division ? `${upazila.name}, ${district.name}, ${division.name}` : 'Location not set';
}

export function PatientDashboardPage() {
  const [showEditProfile, setShowEditProfile] = useState(false);
  const [bloodDonorStatus, setBloodDonorStatus] = useState(false);
  const [selectedInterest, setSelectedInterest] = useState<'YES' | 'NO'>('YES');
  const [isLoadingInterest, setIsLoadingInterest] = useState(true);
  const [selectedDivisionId, setSelectedDivisionId] = useState<number | null>(null);
  const [selectedDistrictId, setSelectedDistrictId] = useState<number | null>(null);
  const [selectedUpazilaId, setSelectedUpazilaId] = useState<number | null>(null);

  const { divisions, districts, upazilas, getDistrictsByDivision, getUpazilasByDistrict } = useLocationStore();

  const [profile, setProfile] = useState({
    firstName: '', lastName: '', email: '', phone: '', gender: '', area: '', bloodType: '', age: 0, weight: 0, upazilaId: null as number | null,
  });

  const [editForm, setEditForm] = useState({
    firstName: '', lastName: '', email: '', gender: 'MALE', area: ''
  });

  const [history, setHistory] = useState({
    donationCount: 0, unitCount: 0, bloodDonationHistoryResponses: [] as DonationRecord[]
  });

  useEffect(() => {
    fetchProfile();
    fetchDonationHistory();
  }, []);

  const fetchProfile = async () => {
    try {
      const { data: res } = await api.get('/api/v1/donor/profile');
      if (res.code === 'XS0001') {
        const p = res.data;
        const user = p.user;
        const upzId = user?.upazila?.id || null;
        setProfile({
          firstName: user?.fname || '', lastName: user?.lname || '', email: user?.email || '',
          phone: user?.phone || '', gender: user?.gender || '', area: user?.area || '',
          bloodType: p.bloodType || '', age: p.age || 0, weight: p.weight || 0, upazilaId: upzId,
        });
        setEditForm({
          firstName: user?.fname || '', lastName: user?.lname || '', email: user?.email || '',
          gender: user?.gender || 'MALE', area: user?.area || ''
        });
        setSelectedInterest(p.interested === 'YES' ? 'YES' : 'NO');
        setBloodDonorStatus(p.interested === 'YES');
        setIsLoadingInterest(false);
        if (upzId) {
          const upz = upazilas.find(u => u.id === upzId);
          const dist = upz && districts.find(d => d.id === upz.district_id);
          const div = dist && divisions.find(v => v.id === dist.division_id);
          if (div) setSelectedDivisionId(div.id);
          if (dist) setSelectedDistrictId(dist.id);
          if (upz) setSelectedUpazilaId(upz.id);
        }
      }
    } catch (e) {
      console.error('Error fetching donor profile:', e);
      setIsLoadingInterest(false);
    }
  };

  const fetchDonationHistory = async () => {
    try {
      const { data: res } = await api.get('/api/v1/donor/donation-history');
      if (res.code === 'XS0001') {
        setHistory({
          donationCount: res.data.donationCount || 0,
          unitCount: res.data.unitCount || 0,
          bloodDonationHistoryResponses: res.data.bloodDonationHistoryResponses || [],
        });
      }
    } catch (e) {
      console.error('Error fetching donation history:', e);
    }
  };

  const updateDonationInterest = async (interest: 'YES' | 'NO') => {
    try {
      const { data } = await api.put('/api/v1/donor/update-interest', { interested: interest });
      if (data.code === 'XS0001') {
        toast.success('Donation interest updated!');
        setBloodDonorStatus(interest === 'YES');
      } else {
        toast.error(data.message || 'Failed to update interest.');
      }
    } catch (e: any) {
      toast.error(e?.response?.data?.message || 'Something went wrong.');
    }
  };

  const handleProfileSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedUpazilaId) return toast.error('Please select upazila');
    try {
      await api.put('/api/v1/user/user-profile-update', {
        ...editForm, upazilaId: selectedUpazilaId
      });
      toast.success('Profile updated successfully');
      setShowEditProfile(false);
      fetchProfile();
    } catch (error) {
      toast.error('Failed to update profile');
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-5xl mx-auto space-y-8">
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-bold">My Account</h1>
          <button onClick={() => setShowEditProfile(true)} className="text-sm bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
            Edit Profile
          </button>
        </div>

        <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-lg font-semibold mb-4">Personal Information</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
            <div className="flex items-center gap-2"><User className="text-gray-400" size={20} />{profile.firstName} {profile.lastName}</div>
            <div className="flex items-center gap-2"><Phone className="text-gray-400" size={20} />{profile.phone}</div>
            <div className="flex items-center gap-2"><Mail className="text-gray-400" size={20} />{profile.email}</div>
            <div className="flex items-center gap-2"><MapPin className="text-gray-400" size={20} />{getFullLocationName(profile.upazilaId, upazilas, districts, divisions)}</div>
            <div className="flex items-center gap-2"><User className="text-gray-400" size={20} />{profile.gender}</div>
            <div className="flex items-center gap-2"><MapPin className="text-gray-400" size={20} />{profile.area}</div>
          </div>
        </div>

        {/* Other sections: Donation Summary, History, Interest buttons remain unchanged */}

         {/* Donation Summary */}
         <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-lg font-semibold mb-4">Donation Summary</h2>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            <div className="bg-red-100 p-4 rounded text-center">
              <Activity className="mx-auto text-red-500" />
              <p className="text-lg font-bold">{history.donationCount}</p>
              <p className="text-sm text-gray-600">Total Donations</p>
            </div>
            <div className="bg-blue-100 p-4 rounded text-center">
              <Heart className="mx-auto text-blue-500" />
              <p className="text-lg font-bold">{history.unitCount}</p>
              <p className="text-sm text-gray-600">Total Units</p>
            </div>
            <div className="bg-green-100 p-4 rounded text-center">
              <User className="mx-auto text-green-500" />
              <p className="text-lg font-bold">{profile.age} yrs</p>
              <p className="text-sm text-gray-600">Age</p>
            </div>
            <div className="bg-yellow-100 p-4 rounded text-center">
              <User className="mx-auto text-yellow-500" />
              <p className="text-lg font-bold">{profile.weight} kg</p>
              <p className="text-sm text-gray-600">Weight</p>
            </div>
          </div>
        </div>

        {/* Donation History */}
        <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-lg font-semibold mb-4">Donation History</h2>
          {history.bloodDonationHistoryResponses.length > 0 ? (
            <div className="space-y-4">
              {history.bloodDonationHistoryResponses.map((record, idx) => (
                <div key={idx} className="border p-4 rounded">
                  <div className="flex justify-between items-start">
                    <div>
                      <div className="font-medium">{record.recipient}</div>
                      <div className="text-sm text-gray-500 flex items-center gap-2">
                        <Calendar size={16} /> {new Date(record.date).toLocaleDateString()}
                      </div>
                      <div className="text-sm text-gray-500 flex items-center gap-2 mt-1">
                        <MapPin size={16} /> {record.location}
                      </div>
                    </div>
                    <div className="text-right">
                      <div className="bg-red-100 text-red-600 px-3 py-1 rounded-full text-sm inline-block">{record.bloodGroup}</div>
                      <div className="text-sm text-gray-600 mt-1">
                        {record.units} unit{record.units > 1 ? 's' : ''}
                      </div>
                    </div>
                  </div>
                  {record.certificate && (
                    <a
                      href={record.certificate}
                      target="_blank"
                      rel="noreferrer"
                      className="text-blue-500 text-sm hover:underline mt-2 inline-block"
                    >
                      View Certificate →
                    </a>
                  )}
                </div>
              ))}
            </div>
          ) : (
            <div className="text-center text-gray-500 py-4">No donation history available.</div>
          )}
        </div>

        {/* Interest Update */}
        <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-lg font-semibold mb-4">Do you want to donate blood?</h2>
          {isLoadingInterest ? (
            <p className="text-sm text-gray-500">Loading interest status...</p>
          ) : (
            <div className="flex items-center gap-4 flex-wrap">
              {['YES', 'NO'].map(value => (
                <button
                  key={value}
                  type="button"
                  onClick={() => setSelectedInterest(value as 'YES' | 'NO')}
                  className={`px-5 py-2 rounded-full border transition-all ${
                    selectedInterest === value
                      ? value === 'YES'
                        ? 'bg-green-500 text-white border-green-500'
                        : 'bg-red-500 text-white border-red-500'
                      : 'bg-gray-100 text-gray-700 border-gray-300 hover:bg-gray-200'
                  }`}
                >
                  {value === 'YES' ? 'Yes' : 'No'}
                </button>
              ))}
              <button
                onClick={() => updateDonationInterest(selectedInterest)}
                className="px-5 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-full text-sm"
              >
                Update
              </button>
            </div>
          )}
        </div>

        <Dialog open={showEditProfile} onClose={() => setShowEditProfile(false)} className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-screen px-4">
            <Dialog.Panel className="bg-white p-6 rounded-lg shadow-lg max-w-md w-full">
              <Dialog.Title className="text-lg font-bold mb-4">Edit Profile</Dialog.Title>
              <form onSubmit={handleProfileSubmit} className="space-y-3">
                <input name="firstName" value={editForm.firstName} onChange={e => setEditForm({ ...editForm, firstName: e.target.value })} placeholder="First Name" className="w-full border px-3 py-2 rounded" />
                <input name="lastName" value={editForm.lastName} onChange={e => setEditForm({ ...editForm, lastName: e.target.value })} placeholder="Last Name" className="w-full border px-3 py-2 rounded" />
                <input name="email" value={editForm.email} onChange={e => setEditForm({ ...editForm, email: e.target.value })} placeholder="Email" className="w-full border px-3 py-2 rounded" />
                <select name="gender" value={editForm.gender} onChange={e => setEditForm({ ...editForm, gender: e.target.value })} className="w-full border px-3 py-2 rounded">
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                  <option value="OTHER">Other</option>
                </select>
                <input name="area" value={editForm.area} onChange={e => setEditForm({ ...editForm, area: e.target.value })} placeholder="Area" className="w-full border px-3 py-2 rounded" />

                <select value={selectedDivisionId || ''} onChange={e => { const val = Number(e.target.value); setSelectedDivisionId(val); setSelectedDistrictId(null); setSelectedUpazilaId(null); }} className="w-full border px-3 py-2 rounded">
                  <option value="">Select Division</option>
                  {divisions.map(div => <option key={div.id} value={div.id}>{div.name}</option>)}
                </select>

                {selectedDivisionId && (
                  <select value={selectedDistrictId || ''} onChange={e => { const val = Number(e.target.value); setSelectedDistrictId(val); setSelectedUpazilaId(null); }} className="w-full border px-3 py-2 rounded">
                    <option value="">Select District</option>
                    {getDistrictsByDivision(selectedDivisionId).map(dist => <option key={dist.id} value={dist.id}>{dist.name}</option>)}
                  </select>
                )}

                {selectedDistrictId && (
                  <select value={selectedUpazilaId || ''} onChange={e => setSelectedUpazilaId(Number(e.target.value))} className="w-full border px-3 py-2 rounded">
                    <option value="">Select Upazila</option>
                    {getUpazilasByDistrict(selectedDistrictId).map(upz => <option key={upz.id} value={upz.id}>{upz.name}</option>)}
                  </select>
                )}

                <button type="submit" className="w-full bg-blue-600 text-white py-2 rounded">Update</button>
              </form>
            </Dialog.Panel>
          </div>
        </Dialog>
      </div>
    </div>
  );
}