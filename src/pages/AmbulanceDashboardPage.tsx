import { useEffect, useState } from 'react';
import { Dialog } from '@headlessui/react';
import { toast } from 'react-hot-toast';
import { User, Mail, Phone, MapPin, Truck } from 'lucide-react';
import { api } from '../lib/api';
import { useLocationStore } from '../stores/areaLocationStore';
import type { Division, District, Upazila } from '../stores/areaLocationStore';


const fieldLabels: Record<string, string> = {
    ambulanceType: 'Ambulance Type',
    ambulanceNumber: 'Ambulance Number',
    ambulanceStatus: 'Ambulance Status',
    about: 'About the Ambulance',
    service_offers: 'Service Offers (comma-separated)',
    hospital_affiliation: 'Hospital Affiliation',
    coverage_areas: 'Coverage Areas (comma-separated)',
    response_time: 'Response Time (in minutes)',
    doctors: 'Number of Doctors',
    nurses: 'Number of Nurses',
    paramedics: 'Number of Paramedics',
    team_qualification: 'Team Qualification',
    starting_fee: 'Starting Fee (in BDT)',
  };

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

export function AmbulanceDashboardPage() {
  const [showEdit, setShowEdit] = useState(false);
  const [showCreateAmbulance, setShowCreateAmbulance] = useState(false);
  const [ambulance, setAmbulance] = useState<any>(null);
  const [ambulanceStatus, setAmbulanceStatus] = useState('');
  const [ambulanceId, setAmbulanceId] = useState<number | null>(null);

  const [userId, setUserId] = useState<number | null>(null);

  const [profile, setProfile] = useState({
    name: '', email: '', phone: '', area: '',
  });

  const [editForm, setEditForm] = useState({
    name: '', email: '', phone: '', area: ''
  });

  const [ambulanceForm, setAmbulanceForm] = useState({
    ambulanceType: '', ambulanceNumber: '', ambulanceStatus: 'AVAILABLE',
    about: '', service_offers: '', hospital_affiliation: '', coverage_areas: '',
    response_time: 0, doctors: 0, nurses: 0, paramedics: 0,
    team_qualification: '', starting_fee: 0
  });

  const [selectedDivisionId, setSelectedDivisionId] = useState<number | null>(null);
  const [selectedDistrictId, setSelectedDistrictId] = useState<number | null>(null);
  const [selectedUpazilaId, setSelectedUpazilaId] = useState<number | null>(null);

  const { divisions, districts, upazilas, getDistrictsByDivision, getUpazilasByDistrict } = useLocationStore();

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const { data } = await api.get('/api/v1/user');
      const p = data?.data;
      setUserId(p?.id);
      const name = `${p?.firstName || p?.fname || ''} ${p?.lastName || p?.lname || ''}`.trim();

      setProfile({ name, email: p?.email || '', phone: p?.phone || '', area: p?.area || '' });
      setEditForm({ name, email: p?.email || '', phone: p?.phone || '', area: p?.area || '' });

      if (p?.upazila) {
        const upazila = upazilas.find(u => u.id === p.upazila.id);
        const district = upazila && districts.find(d => d.id === upazila.district_id);
        const division = district && divisions.find(v => v.id === district.division_id);
        if (division) setSelectedDivisionId(division.id);
        if (district) setSelectedDistrictId(district.id);
        if (upazila) setSelectedUpazilaId(upazila.id);
      }

      // ✅ Fix: use correct API to fetch ambulance by user ID
      const ambulanceRes = await api.get(`/api/v1/ambulance/${p?.id}`);
      const amb = ambulanceRes.data?.data;

      if (amb?.id) {
        setAmbulance(amb);
        setAmbulanceId(amb.id);
        setAmbulanceStatus(amb.ambulanceStatus);
      } else {
        setAmbulance(null);
        setAmbulanceId(null);
        setAmbulanceStatus('');
      }
    } catch (err) {
      console.warn('Ambulance not found or failed to fetch:', err);
      setAmbulance(null); // ⛔ Fix blank state issue
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
        upazilaId: selectedUpazilaId, gender: 'OTHER',
      });
      toast.success('Profile updated!');
      setShowEdit(false);
      fetchProfile();
    } catch (err: any) {
      console.error('[Update Error]', err?.response?.data || err);
      toast.error('Failed to update profile');
    }
  };

  const handleCreateAmbulance = async () => {
    try {
      const res = await api.post('/api/v1/ambulance/create', ambulanceForm);
      toast.success('Ambulance created successfully!');
      setShowCreateAmbulance(false);
      fetchProfile();
    } catch (err: any) {
      console.error('[Create Ambulance Error]', err?.response?.data || err);
      toast.error(err?.response?.data?.message || 'Creation failed');
    }
  };

  const handleStatusChange = async (e: React.ChangeEvent<HTMLSelectElement>) => {
    const newStatus = e.target.value;
    try {
      await api.put(`/api/v1/ambulance/${ambulanceId}/status?status=${newStatus}`);
      toast.success('Ambulance status updated!');
      setAmbulanceStatus(newStatus);
    } catch (err: any) {
      console.error('[Status Update Error]', err?.response?.data || err);
      toast.error('Failed to update ambulance status');
    }
  };
  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-4xl mx-auto space-y-8">
        <div className="flex justify-between items-center">
          <h1 className="text-2xl font-bold">Ambulance Management</h1>
          <button
            onClick={() => setShowEdit(true)}
            className="bg-blue-600 text-white px-4 py-2 text-sm rounded hover:bg-blue-700"
          >
            Edit Info
          </button>
        </div>
  
        {/* Profile Info */}
        <div className="bg-white p-6 rounded-lg shadow space-y-3">
          <h2 className="text-lg font-semibold">User Information</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div className="flex items-center gap-2"><User size={20} />{profile.name}</div>
            <div className="flex items-center gap-2"><Phone size={20} />{profile.phone}</div>
            <div className="flex items-center gap-2"><Mail size={20} />{profile.email}</div>
            <div className="flex items-center gap-2"><MapPin size={20} />{getFullLocationName(selectedUpazilaId, upazilas, districts, divisions)}</div>
            <div className="flex items-center gap-2"><MapPin size={20} />{profile.area}</div>
          </div>
        </div>
  
        {/* Ambulance Info */}
        {ambulance ? (
          <div className="bg-white p-6 rounded-lg shadow space-y-3">
            <h2 className="text-lg font-semibold flex items-center gap-2"><Truck size={20} />Ambulance Details</h2>
            <p><strong>Type:</strong> {ambulance.ambulanceType}</p>
            <p><strong>Number:</strong> {ambulance.ambulanceNumber}</p>
            <p><strong>About:</strong> {ambulance.about}</p>
            <p><strong>Status:</strong> {ambulanceStatus}</p>
            <p><strong>Coverage:</strong> {ambulance.coverageAreas?.join(', ')}</p>
            <p><strong>Doctors:</strong> {ambulance.doctors}, <strong>Nurses:</strong> {ambulance.nurses}, <strong>Paramedics:</strong> {ambulance.paramedics}</p>
  
            {/* Status Dropdown */}
            <div className="mt-4">
              <label className="block text-sm font-medium">Update Status:</label>
              <select
                value={ambulanceStatus}
                onChange={handleStatusChange}
                className="mt-1 border px-3 py-2 rounded w-full"
              >
                <option value="AVAILABLE">AVAILABLE</option>
                <option value="ON_TRIP">ON_TRIP</option>
                <option value="MAINTENANCE">MAINTENANCE</option>
              </select>
            </div>
          </div>
        ) : (
          <div className="bg-white p-6 rounded-lg shadow">
            <h2 className="text-lg font-semibold mb-2">You haven't created an ambulance yet</h2>
            <button
              onClick={() => setShowCreateAmbulance(true)}
              className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
            >
              Create Ambulance
            </button>
          </div>
        )}
  
        {/* Edit Profile Modal */}
        <Dialog open={showEdit} onClose={() => setShowEdit(false)} className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-screen px-4">
            <Dialog.Panel className="bg-white p-6 rounded shadow max-w-md w-full">
              <Dialog.Title className="text-lg font-bold mb-4">Edit Info</Dialog.Title>
              <form onSubmit={handleProfileUpdate} className="space-y-3">
                <input value={editForm.name} onChange={e => setEditForm({ ...editForm, name: e.target.value })} placeholder="Full Name" className="w-full border px-3 py-2 rounded" />
                <input value={editForm.email} onChange={e => setEditForm({ ...editForm, email: e.target.value })} placeholder="Email" className="w-full border px-3 py-2 rounded" />
                <input value={editForm.phone} onChange={e => setEditForm({ ...editForm, phone: e.target.value })} placeholder="Phone" className="w-full border px-3 py-2 rounded" />
                <input value={editForm.area} onChange={e => setEditForm({ ...editForm, area: e.target.value })} placeholder="Area" className="w-full border px-3 py-2 rounded" />
                <select value={selectedDivisionId || ''} onChange={e => { const val = +e.target.value; setSelectedDivisionId(val); setSelectedDistrictId(null); setSelectedUpazilaId(null); }} className="w-full border px-3 py-2 rounded">
                  <option value="">Select Division</option>
                  {divisions.map(div => <option key={div.id} value={div.id}>{div.name}</option>)}
                </select>
                {selectedDivisionId && (
                  <select value={selectedDistrictId || ''} onChange={e => { const val = +e.target.value; setSelectedDistrictId(val); setSelectedUpazilaId(null); }} className="w-full border px-3 py-2 rounded">
                    <option value="">Select District</option>
                    {getDistrictsByDivision(selectedDivisionId).map(dist => <option key={dist.id} value={dist.id}>{dist.name}</option>)}
                  </select>
                )}
                {selectedDistrictId && (
                  <select value={selectedUpazilaId || ''} onChange={e => setSelectedUpazilaId(+e.target.value)} className="w-full border px-3 py-2 rounded">
                    <option value="">Select Upazila</option>
                    {getUpazilasByDistrict(selectedDistrictId).map(upz => <option key={upz.id} value={upz.id}>{upz.name}</option>)}
                  </select>
                )}
                <button type="submit" className="w-full bg-blue-600 text-white py-2 rounded">Update</button>
              </form>
            </Dialog.Panel>
          </div>
        </Dialog>
  
        {/* Create Ambulance Modal */}
        <Dialog open={showCreateAmbulance} onClose={() => setShowCreateAmbulance(false)} className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-screen px-4">
            <Dialog.Panel className="bg-white p-6 rounded shadow max-w-lg w-full">
              <Dialog.Title className="text-lg font-bold mb-4">Create Ambulance</Dialog.Title>
              <div className="space-y-2">
                {Object.entries(ambulanceForm).map(([key, val]) => (
                  <div key={key} className="space-y-1">
                    <label className="text-sm font-medium block">{fieldLabels[key]}</label>
                    <input
                      type={typeof val === 'number' ? 'number' : 'text'}
                      value={val}
                      onChange={e => setAmbulanceForm({ ...ambulanceForm, [key]: key.includes('time') || key.includes('fee') || ['doctors', 'nurses', 'paramedics'].includes(key) ? +e.target.value : e.target.value })}
                      placeholder={fieldLabels[key]}
                      className="w-full border px-3 py-2 rounded"
                    />
                  </div>
                ))}
                <button onClick={handleCreateAmbulance} className="w-full bg-green-600 text-white py-2 rounded mt-2">
                  Submit
                </button>
              </div>
            </Dialog.Panel>
          </div>
        </Dialog>
      </div>
    </div>
  );
  
 
}
