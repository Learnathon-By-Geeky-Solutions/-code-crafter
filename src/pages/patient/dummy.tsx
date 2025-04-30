
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import {
  Calendar, Clock, FileText, Activity, ToggleLeft, Download, Star, X,
  Mail, Phone, User, MapPin
} from 'lucide-react';
import { api } from '../../lib/api'; 
import { EditProfileModal } from './EditProfileModal';

interface Appointment {
  id: string;
  doctorName: string;
  doctorSpecialty: string;
  doctorImage: string;
  date: string;
  time: string;
  type: 'upcoming' | 'current' | 'past';
  status: 'scheduled' | 'completed' | 'cancelled';
  prescriptionUrl?: string;
  rating?: number;
}

interface PatientProfile {
  name: string;
  email: string;
  phone: string;
  dateOfBirth: string;
  gender: string;
  address: string;
  bloodGroup: string;
  emergencyContact: string;
  allergies: string;
  medicalConditions: string;
}

const appointments: Appointment[] = [
  {
    id: '1',
    doctorName: 'Dr. Sarah Johnson',
    doctorSpecialty: 'Cardiologist',
    doctorImage: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80',
    date: '2024-03-20',
    time: '10:00 AM',
    type: 'upcoming',
    status: 'scheduled'
  },
  {
    id: '2',
    doctorName: 'Dr. Michael Chen',
    doctorSpecialty: 'Neurologist',
    doctorImage: 'https://images.unsplash.com/photo-1537368910025-700350fe46c7?auto=format&fit=crop&w=150&h=150&q=80',
    date: '2024-03-15',
    time: '2:30 PM',
    type: 'past',
    status: 'completed',
    prescriptionUrl: '/prescriptions/sample.pdf',
    rating: 5
  }
];

export function PatientDashboardPage() {
  const [activeTab, setActiveTab] = useState<'upcoming' | 'current' | 'past'>('upcoming');
  const [bloodDonorStatus, setBloodDonorStatus] = useState(true);
  const [showEditProfile, setShowEditProfile] = useState(false);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [showErrorModal, setShowErrorModal] = useState(false);

  const [profile, setProfile] = useState<PatientProfile>({
    name: 'John Doe',
    email: 'john.doe@example.com',
    phone: '+880 1234-567890',
    dateOfBirth: '1990-01-01',
    gender: 'male',
    address: '123 Main St, Dhaka',
    bloodGroup: 'A+',
    emergencyContact: '+880 1234-567891',
    allergies: 'None',
    medicalConditions: 'None'
  });

  const [bloodDonationStats, setBloodDonationStats] = useState({
    totalDonations: 0,
    lastDonation: '',
    bloodGroup: profile.bloodGroup,
    livesSaved: 0
  });

  useEffect(() => {
    fetchBloodDonationStats();
    fetchUserProfile();
  }, []);

  const fetchBloodDonationStats = async () => {
    try {
      const { data: baseResponse } = await api.get('/api/v1/donor/donation-history');
      if (baseResponse.code === 'XS0001') {
        const response = baseResponse.data;
        setBloodDonationStats({
          totalDonations: response.donationCount || 0,
          lastDonation: response.bloodDonationHistoryResponses?.length
            ? response.bloodDonationHistoryResponses[0].donationDate
            : '',
          bloodGroup: profile.bloodGroup,
          livesSaved: response.unitCount || 0
        });
      }
    } catch (error) {
      console.error('Failed to fetch blood donation stats', error);
    }
  };

  const fetchUserProfile = async () => {
    try {
      const { data: baseResponse } = await api.get('/api/v1/user');
      if (baseResponse.code === 'XS0001') {
        const user = baseResponse.data;
        setProfile(prev => ({
          ...prev,
          name: `${user.fname} ${user.lname}`,
          email: user.email,
          phone: user.phone,
          address: `${user.area}, ${user.upazila.name}`
          // Keeping bloodGroup, emergencyContact, allergies, medicalConditions unchanged
        }));
      }
    } catch (error) {
      console.error('Failed to fetch user profile', error);
    }
  };

  const updateDonationInterest = async (interest: 'YES' | 'NO') => {
    try {
      await api.put('/api/v1/donor/update-interest', { interested: interest });
      setBloodDonorStatus(interest === 'YES');
    } catch (error) {
      console.error('Failed to update donation interest', error);
      setShowErrorModal(true);
    }
  };

  const filteredAppointments = appointments.filter(apt => apt.type === activeTab);

  const handleProfileUpdate = (e: React.FormEvent) => {
    e.preventDefault();
    setShowEditProfile(false);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-6xl mx-auto">

        {/* Top Heading */}
        <div className="flex items-center justify-between mb-8">
          <h1 className="text-2xl font-bold">My Account</h1>
          <button 
            className="text-blue-500 hover:text-blue-600"
            onClick={() => setShowEditProfile(true)}
          >
            Edit Profile
          </button>
        </div>

        {/* Profile Information */}
        <div className="bg-white rounded-lg shadow-sm p-6 mb-8">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <h2 className="text-lg font-semibold mb-4">Personal Information</h2>
              <div className="space-y-3">
                <div className="flex items-center gap-2"><User size={20} className="text-gray-400" /><span>{profile.name}</span></div>
                <div className="flex items-center gap-2"><Mail size={20} className="text-gray-400" /><span>{profile.email}</span></div>
                <div className="flex items-center gap-2"><Phone size={20} className="text-gray-400" /><span>{profile.phone}</span></div>
                <div className="flex items-center gap-2"><MapPin size={20} className="text-gray-400" /><span>{profile.address}</span></div>
              </div>
            </div>

            <div>
              <h2 className="text-lg font-semibold mb-4">Medical Information</h2>
              <div className="space-y-3">
                <div className="flex items-center gap-2"><span className="font-medium">Blood Group:</span><span>{profile.bloodGroup}</span></div>
                <div className="flex items-center gap-2"><span className="font-medium">Emergency Contact:</span><span>{profile.emergencyContact}</span></div>
                <div className="flex items-center gap-2"><span className="font-medium">Allergies:</span><span>{profile.allergies}</span></div>
                <div className="flex items-center gap-2"><span className="font-medium">Medical Conditions:</span><span>{profile.medicalConditions}</span></div>
              </div>
            </div>
          </div>
        </div>

        {/* Blood Donation Status */}
        <div className="bg-white rounded-lg shadow-sm p-6 mb-8">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-semibold">Blood Donation Status</h2>
            <div className="flex items-center gap-2">
              <span className="text-sm text-gray-600">Available for donation</span>
              <button
                className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                  bloodDonorStatus ? 'bg-blue-500' : 'bg-gray-300'
                }`}
                onClick={() => {
                  if (bloodDonorStatus) {
                    setShowConfirmModal(true);
                  } else {
                    updateDonationInterest('YES');
                  }
                }}
              >
                <span
                  className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                    bloodDonorStatus ? 'translate-x-6' : 'translate-x-1'
                  }`}
                />
              </button>
            </div>
          </div>

          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            <div className="bg-blue-50 rounded-lg p-4">
              <Activity className="text-blue-500 mb-2" size={24} />
              <div className="text-2xl font-bold">{bloodDonationStats.totalDonations}</div>
              <div className="text-sm text-gray-600">Total Donations</div>
            </div>
            <div className="bg-green-50 rounded-lg p-4">
              <Calendar className="text-green-500 mb-2" size={24} />
              <div className="text-2xl font-bold">
                {bloodDonationStats.lastDonation
                  ? new Date(bloodDonationStats.lastDonation).toLocaleDateString()
                  : 'N/A'}
              </div>
              <div className="text-sm text-gray-600">Last Donation</div>
            </div>
            <div className="bg-red-50 rounded-lg p-4">
              <ToggleLeft className="text-red-500 mb-2" size={24} />
              <div className="text-2xl font-bold">{bloodDonationStats.bloodGroup}</div>
              <div className="text-sm text-gray-600">Blood Group</div>
            </div>
            <div className="bg-purple-50 rounded-lg p-4">
              <Activity className="text-purple-500 mb-2" size={24} />
              <div className="text-2xl font-bold">{bloodDonationStats.livesSaved}</div>
              <div className="text-sm text-gray-600">Lives Saved</div>
            </div>
          </div>
        </div>

        {/* Appointments */}
        {/* Your appointments list code would go here */}

      </div>

      {/* Confirm Turn Off Modal */}
      {showConfirmModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-lg p-6 max-w-sm w-full">
            <h2 className="text-xl font-bold mb-4">Confirm Action</h2>
            <p className="text-gray-600 mb-6">
              Are you sure you want to mark yourself as unavailable for blood donation?
            </p>
            <div className="flex gap-4">
              <button
                className="flex-1 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600"
                onClick={() => {
                  updateDonationInterest('NO');
                  setShowConfirmModal(false);
                }}
              >
                Yes, Confirm
              </button>
              <button
                className="flex-1 py-2 bg-gray-200 rounded-lg hover:bg-gray-300"
                onClick={() => setShowConfirmModal(false)}
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Error Modal */}
      {showErrorModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-lg p-6 max-w-sm w-full text-center">
            <h2 className="text-xl font-bold text-red-500 mb-4">Error</h2>
            <p className="text-gray-600 mb-6">Something went wrong while updating your donation status.</p>
            <button
              className="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
              onClick={() => setShowErrorModal(false)}
            >
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
