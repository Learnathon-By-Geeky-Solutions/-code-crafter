import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { toast } from 'react-hot-toast';
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
  const [bloodDonorStatus, setBloodDonorStatus] = useState(false);
  const [showEditProfile, setShowEditProfile] = useState(false);
  const [profile, setProfile] = useState({
    name: '',
    email: '',
    phone: '',
    dateOfBirth: '',
    gender: '',
    address: '',
    bloodGroup: '',
    emergencyContact: '',
    allergies: '',
    medicalConditions: '',
  });
  const [editProfile, setEditProfile] = useState({
    firstName: '',
    lastName: '',
    email: '',
    gender: '',
    area: '',
  });
  const [bloodDonationStats, setBloodDonationStats] = useState({
    totalDonations: 0,
    lastDonation: '',
    bloodGroup: '',
    livesSaved: 0
  });

  useEffect(() => {
    fetchBloodDonationStats();
    fetchUserProfile();
    fetchDonationInterest();
  }, []);
  

  const fetchBloodDonationStats = async () => {
    try {
      const { data: baseResponse } = await api.get('/api/v1/donor/donation-history');
      if (baseResponse.code === 'XS0001') {
        const response = baseResponse.data;
        setBloodDonationStats({
          totalDonations: response.donationCount || 0,
          lastDonation: response.bloodDonationHistoryResponses?.[0]?.donationDate || '',
          bloodGroup: response.bloodType || '',
          livesSaved: response.unitCount || 0
        });
      }
    } catch (error) {
      console.error('Failed to fetch blood donation stats', error);
    }
  };

  const [isLoadingInterest, setIsLoadingInterest] = useState(true);


  const fetchDonationInterest = async () => {
    try {
      setIsLoadingInterest(true); // start loading
      const { data: baseResponse } = await api.get('/api/v1/donor');
      if (baseResponse.code === 'XS0001') {
        const response = baseResponse.data;
        setBloodDonorStatus(response.interested === 'YES');
      }
    } catch (error) {
      console.error('Failed to fetch donation interest', error);
    } finally {
      setIsLoadingInterest(false); // loading finished
    }
  };
  

  const fetchUserProfile = async () => {
    try {
      const { data: baseResponse } = await api.get('/api/v1/user');
      if (baseResponse.code === 'XS0001') {
        const user = baseResponse.data;
        setProfile({
          name: `${user.fname} ${user.lname}`,
          email: user.email,
          phone: user.phone,
          dateOfBirth: '',
          gender: user.gender || '',
          address: `${user.area}, ${user.upazila?.name || ''}`,
          bloodGroup: '',
          emergencyContact: '',
          allergies: '',
          medicalConditions: '',
        });
        setEditProfile({
          firstName: user.fname || '',
          lastName: user.lname || '',
          email: user.email || '',
          gender: user.gender || '',
          area: user.area || '',
        });
      }
    } catch (error) {
      console.error('Failed to fetch user profile', error);
    }
  };

  const updateDonationInterest = async (interest: 'YES' | 'NO') => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        console.error('No token found.');
        return;
      }
      const { data: updateResponse } = await api.put(
        '/api/v1/donor/update-interest',
        { interested: interest },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      if (updateResponse.code === 'XS0001') {
        toast.success('Donation interest updated!');
      } else {
        toast.error(updateResponse.message || 'Failed to update interest.');
      }
    } catch (error: any) {
      console.error('Failed to update donation interest', error?.response?.data || error);
      toast.error('Something went wrong.');
    }
  };

  const filteredAppointments = appointments.filter(apt => apt.type === activeTab);

  const handleSwitchToggle = async () => {
    const newStatus = !bloodDonorStatus;
    setBloodDonorStatus(newStatus);
    await updateDonationInterest(newStatus ? 'YES' : 'NO');
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
              {!isLoadingInterest && (
  <button
    onClick={handleSwitchToggle}
    className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${bloodDonorStatus ? 'bg-blue-500' : 'bg-gray-300'}`}
  >
    <span
      className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${bloodDonorStatus ? 'translate-x-6' : 'translate-x-1'}`}
    />
  </button>
)}

            </div>
          </div>

          {/* Donation Statistics */}
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
            <div className="bg-purple-50 rounded-lg p-4">
              <Activity className="text-purple-500 mb-2" size={24} />
              <div className="text-2xl font-bold">{bloodDonationStats.livesSaved}</div>
              <div className="text-sm text-gray-600">Lives Saved</div>
            </div>
          </div>
        </div>

      </div>

      {/* Confirm Modal */}
      {/* (removed confirm modal because switch is now handled instantly) */}
    </div>
  );
}
