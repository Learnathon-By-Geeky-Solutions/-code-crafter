import { useEffect, useState } from 'react';
import { Dialog } from '@headlessui/react';
import { toast } from 'react-hot-toast';
import { api } from '../../lib/api';

const specialistCategories = [
  'CARDIOLOGY',
  'ONCOPLASTIC_BREAST_SURGEON',
  'DERMATOLOGY',
  'NEUROLOGY',
  'ORTHOPEDICS'
];

export function DoctorDashboardPage() {
  const [doctor, setDoctor] = useState<any>(null);
  const [showEdit, setShowEdit] = useState(false);
  const [formData, setFormData] = useState({
    doctorTitle: 'Dr',
    specialistCategory: '',
    dateOfBirth: '',
    nid: '',
    passport: '',
    registrationNo: '',
    experience: 0,
    hospital: '',
    about: '',
    areaOfExpertise: '',
    patientCarePolicy: '',
    education: '',
    experienceInfo: '',
    awards: '',
    publications: ''
  });

  const fetchDoctorProfile = async () => {
    try {
      const res = await api.get('/api/v1/doctors/me');
      const id = res.data?.data?.id;
      if (!id) {
        setDoctor(null);
        return;
      }
      const profileRes = await api.get(`/api/v1/doctors/${id}`);
      setDoctor(profileRes.data?.data);
    } catch (err) {
      console.warn('Doctor profile not found.');
      setDoctor(null);
    }
  };

  useEffect(() => {
    fetchDoctorProfile();
  }, []);

  const handleCreateProfile = async () => {
    const {
      doctorTitle,
      specialistCategory,
      dateOfBirth,
      nid,
      registrationNo,
      experience,
      hospital,
      about
    } = formData;

    if (
      !doctorTitle || !specialistCategory || !dateOfBirth ||
      !nid || !registrationNo || !hospital || !about || !experience
    ) {
      toast.error('Please fill all required fields');
      return;
    }

    try {
      await api.post('/api/v1/doctors/profile', formData);
      toast.success('Doctor profile created');
      fetchDoctorProfile();
    } catch (err: any) {
      console.error('[Create Error]', err?.response?.data || err);
      toast.error(err?.response?.data?.message || 'Failed to create doctor profile');
    }
  };

  const handleUpdateProfile = async () => {
    try {
      await api.put(`/api/v1/doctors/${doctor.id}`, formData);
      toast.success('Profile updated');
      setShowEdit(false);
      fetchDoctorProfile();
    } catch (err: any) {
      console.error(err);
      toast.error('Failed to update profile');
    }
  };

  return (
    <div className="max-w-4xl mx-auto p-6">
      <h1 className="text-2xl font-bold mb-6">Doctor Dashboard</h1>

      {doctor ? (
        <div className="bg-white shadow p-4 rounded space-y-2">
          <div><strong>Name:</strong> {doctor.doctorTitle} {doctor.name}</div>
          <div><strong>Specialist:</strong> {doctor.specialist}</div>
          <div><strong>Phone:</strong> {doctor.phone}</div>
          <div><strong>Email:</strong> {doctor.email}</div>
          <div><strong>Address:</strong> {doctor.address}</div>
          <div><strong>Location:</strong> {doctor.upazila.name}, {doctor.upazila.district.name}, {doctor.upazila.district.division.name}</div>
          <div><strong>About:</strong> {doctor.about}</div>
          <div><strong>Expertise:</strong> {doctor.areasOfExpertise?.join(', ')}</div>
          <div><strong>Patient Care Philosophy:</strong> {doctor.patientCarePhilosophy?.join(', ')}</div>
          <div><strong>Education:</strong> {doctor.education?.join(', ')}</div>
          <div><strong>Experience:</strong> {doctor.experience} years</div>
          <div><strong>Awards:</strong> {doctor.awards?.join(', ')}</div>
          <div><strong>Publications:</strong> {doctor.publications?.join(', ')}</div>

          <button
            onClick={() => setShowEdit(true)}
            className="mt-4 bg-blue-600 text-white px-4 py-2 rounded"
          >
            Edit Profile
          </button>
        </div>
      ) : (
        <div className="bg-white p-4 shadow rounded space-y-2">
          <p className="mb-4 font-semibold">No profile found. Please fill the form below to create one.</p>
          {Object.entries(formData).map(([key, value]) => (
            <div key={key}>
              <label className="block text-sm font-medium capitalize">{key}</label>
              {key === 'specialistCategory' ? (
                <select
                  value={value}
                  onChange={e => setFormData({ ...formData, [key]: e.target.value })}
                  className="w-full border px-3 py-2 rounded"
                >
                  <option value="">Select Specialist</option>
                  {specialistCategories.map(cat => (
                    <option key={cat} value={cat}>{cat.replace(/_/g, ' ')}</option>
                  ))}
                </select>
              ) : (
                <input
                  type={key === 'experience' ? 'number' : key === 'dateOfBirth' ? 'date' : 'text'}
                  value={value as any}
                  onChange={e => setFormData({ ...formData, [key]: key === 'experience' ? +e.target.value : e.target.value })}
                  className="w-full border px-3 py-2 rounded"
                />
              )}
            </div>
          ))}
          <button
            onClick={handleCreateProfile}
            className="mt-4 bg-green-600 text-white px-4 py-2 rounded"
          >
            Create Profile
          </button>
        </div>
      )}

      {/* Edit Modal */}
      <Dialog open={showEdit} onClose={() => setShowEdit(false)} className="fixed z-10 inset-0 overflow-y-auto">
        <div className="flex items-center justify-center min-h-screen px-4">
          <Dialog.Panel className="bg-white p-6 rounded shadow max-w-xl w-full">
            <Dialog.Title className="text-lg font-bold mb-4">Edit Doctor Profile</Dialog.Title>
            <div className="space-y-2">
              {Object.entries(formData).map(([key, value]) => (
                <div key={key}>
                  <label className="block text-sm font-medium capitalize">{key}</label>
                  {key === 'specialistCategory' ? (
                    <select
                      value={value}
                      onChange={e => setFormData({ ...formData, [key]: e.target.value })}
                      className="w-full border px-3 py-2 rounded"
                    >
                      <option value="">Select Specialist</option>
                      {specialistCategories.map(cat => (
                        <option key={cat} value={cat}>{cat.replace(/_/g, ' ')}</option>
                      ))}
                    </select>
                  ) : (
                    <input
                      type={key === 'experience' ? 'number' : key === 'dateOfBirth' ? 'date' : 'text'}
                      value={value as any}
                      onChange={e => setFormData({ ...formData, [key]: key === 'experience' ? +e.target.value : e.target.value })}
                      className="w-full border px-3 py-2 rounded"
                    />
                  )}
                </div>
              ))}
              <button
                onClick={handleUpdateProfile}
                className="mt-4 w-full bg-blue-600 text-white py-2 rounded"
              >
                Save Changes
              </button>
            </div>
          </Dialog.Panel>
        </div>
      </Dialog>
    </div>
  );
}