// ✅ Updated PharmacyDashboardPage with Area + Division, District, Upazila Logic

import { useEffect, useState } from 'react';
import { Dialog } from '@headlessui/react';
import { toast } from 'react-hot-toast';
import { User, Mail, Phone, MapPin } from 'lucide-react';
import { api } from '../../lib/api';
import { useLocationStore } from '../../stores/areaLocationStore';
import type { Division, District, Upazila } from '../../stores/areaLocationStore';

function getFullLocationName(
  upazilaId: number | null,
  upazilas: Upazila[],
  districts: District[],
  divisions: Division[]
): string {
  if (!upazilaId) return 'Location not set';
  const upazila = upazilas.find(u => u.id === upazilaId);
  const district = upazila && districts.find(d => d.id === upazila.district_id);
  const division = district && divisions.find(v => v.id === district.division_id);
  return upazila && district && division
    ? `${upazila.name}, ${district.name}, ${division.name}`
    : 'Location not set';
}

export function PharmacyDashboardPage() {
  const [showEdit, setShowEdit] = useState(false);
  const [showLicenseModal, setShowLicenseModal] = useState(false);
  const [licenseSubmitted, setLicenseSubmitted] = useState(false);

  const [profile, setProfile] = useState({
    name: '', email: '', phone: '', area: '', tradeLicenseNumber: '',
  });

  const [editForm, setEditForm] = useState({
    name: '', email: '', phone: '', area: ''
  });

  const [licenseInput, setLicenseInput] = useState('');

  const [selectedDivisionId, setSelectedDivisionId] = useState<number | null>(null);
  const [selectedDistrictId, setSelectedDistrictId] = useState<number | null>(null);
  const [selectedUpazilaId, setSelectedUpazilaId] = useState<number | null>(null);

  const { divisions, districts, upazilas, getDistrictsByDivision, getUpazilasByDistrict } = useLocationStore();

  useEffect(() => {
    fetchProfile();
    const isSubmitted = localStorage.getItem('pharmacy_license_submitted');
    if (isSubmitted === 'true') setLicenseSubmitted(true);
  }, []);

  const fetchProfile = async () => {
    try {
      const { data } = await api.get('/api/v1/user');
      const p = data?.data;
      const name = `${p?.firstName || p?.fname || ''} ${p?.lastName || p?.lname || ''}`.trim();

      setProfile({
        name, email: p?.email || '', phone: p?.phone || '',
        area: p?.area || '', tradeLicenseNumber: p?.tradeLicenseNumber || '',
      });

      setEditForm({
        name, email: p?.email || '', phone: p?.phone || '', area: p?.area || '',
      });

      if (p?.upazila) {
        const upazila = upazilas.find(u => u.id === p.upazila.id);
        const district = upazila && districts.find(d => d.id === upazila.district_id);
        const division = district && divisions.find(v => v.id === district.division_id);
        if (division) setSelectedDivisionId(division.id);
        if (district) setSelectedDistrictId(district.id);
        if (upazila) setSelectedUpazilaId(upazila.id);
      }
    } catch (err) {
      console.error('Failed to load pharmacy profile:', err);
    }
  };

  const handleProfileUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    const nameParts = editForm.name.trim().split(' ');
    const firstName = nameParts[0] || '';
    const lastName = nameParts.slice(1).join(' ') || '';

    if (!selectedUpazilaId) return toast.error('Please select your upazila.');

    try {
      await api.put('/api/v1/user/user-profile-update', {
        firstName, lastName, email: editForm.email, area: editForm.area,
        upazilaId: selectedUpazilaId, gender: 'MALE',
      });
      toast.success('Pharmacy profile updated!');
      setShowEdit(false);
      fetchProfile();
    } catch (err: any) {
      console.error('[Profile Update Error]', err?.response?.data || err);
      toast.error('Failed to update profile');
    }
  };

  const handleLicenseSubmit = async () => {
    try {
      const { data } = await api.post('/api/v1/pharmacy', { tradeLicenseNumber: licenseInput });
      if (data.code === 'XS0001') {
        toast.success('🎉 Congrats! You are now registered as a pharmacy');
        localStorage.setItem('pharmacy_license_submitted', 'true');
        setLicenseSubmitted(true);
        setShowLicenseModal(false);
        fetchProfile();
      } else toast.error(data.message || 'License registration failed');
    } catch (err: any) {
      console.error('[License Submit Error]', err?.response?.data || err);
      toast.error(err?.response?.data?.message || 'Something went wrong');
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-3xl mx-auto space-y-8">
        <div className="flex justify-between items-center">
          <h1 className="text-2xl font-bold">Pharmacy Dashboard</h1>
          <button onClick={() => setShowEdit(true)} className="text-sm bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
            Edit Info
          </button>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-sm space-y-3">
          <h2 className="text-lg font-semibold mb-2">Pharmacy Information</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div className="flex items-center gap-2"><User size={20} className="text-gray-500" />{profile.name}</div>
            <div className="flex items-center gap-2"><Phone size={20} className="text-gray-500" />{profile.phone}</div>
            <div className="flex items-center gap-2"><Mail size={20} className="text-gray-500" />{profile.email}</div>
            <div className="flex items-center gap-2"><MapPin size={20} className="text-gray-500" />{getFullLocationName(selectedUpazilaId, upazilas, districts, divisions)}</div>
            <div className="flex items-center gap-2"><MapPin size={20} className="text-gray-500" />{profile.area}</div>
          </div>
        </div>

        {!licenseSubmitted && (
          <div className="bg-white p-6 rounded-lg shadow-sm">
            <h2 className="text-lg font-semibold mb-4">Be registered as a Pharmacy</h2>
            <button onClick={() => setShowLicenseModal(true)} className="bg-green-600 hover:bg-green-700 text-white px-5 py-2 rounded-full text-sm">
              Submit Trade License Number
            </button>
          </div>
        )}

        <Dialog open={showEdit} onClose={() => setShowEdit(false)} className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-screen px-4">
            <Dialog.Panel className="bg-white p-6 rounded-lg shadow max-w-md w-full">
              <Dialog.Title className="text-lg font-bold mb-4">Edit Pharmacy Info</Dialog.Title>
              <form onSubmit={handleProfileUpdate} className="space-y-3">
                <input name="name" value={editForm.name} onChange={e => setEditForm({ ...editForm, name: e.target.value })} placeholder="Pharmacy Name" className="w-full border px-3 py-2 rounded" />
                <input name="email" value={editForm.email} onChange={e => setEditForm({ ...editForm, email: e.target.value })} placeholder="Email" className="w-full border px-3 py-2 rounded" />
                <input name="phone" value={editForm.phone} onChange={e => setEditForm({ ...editForm, phone: e.target.value })} placeholder="Phone" className="w-full border px-3 py-2 rounded" />
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

        <Dialog open={showLicenseModal} onClose={() => setShowLicenseModal(false)} className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-screen px-4">
            <Dialog.Panel className="bg-white p-6 rounded-lg shadow max-w-sm w-full">
              <Dialog.Title className="text-lg font-semibold mb-3">Enter Trade License Number</Dialog.Title>
              <input value={licenseInput} onChange={e => setLicenseInput(e.target.value)} placeholder="e.g., 1234567890" className="w-full border px-3 py-2 rounded mb-4" />
              <button onClick={handleLicenseSubmit} className="w-full bg-green-600 text-white py-2 rounded">Submit</button>
            </Dialog.Panel>
          </div>
        </Dialog>
      </div>
    </div>
  );
}
