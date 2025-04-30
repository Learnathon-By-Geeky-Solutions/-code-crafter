import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Calendar, Clock, FileText, Activity, ToggleLeft, Download, Star, X, Mail, Phone, User, MapPin } from 'lucide-react';

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

  const bloodDonationStats = {
    totalDonations: 3,
    lastDonation: '2024-02-15',
    bloodGroup: profile.bloodGroup,
    livesSaved: 9
  };

  const filteredAppointments = appointments.filter(apt => apt.type === activeTab);

  const handleProfileUpdate = (e: React.FormEvent) => {
    e.preventDefault();
    // Handle profile update
    setShowEditProfile(false);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-6xl mx-auto">
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
                <div className="flex items-center gap-2">
                  <User className="text-gray-400" size={20} />
                  <span>{profile.name}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Mail className="text-gray-400" size={20} />
                  <span>{profile.email}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Phone className="text-gray-400" size={20} />
                  <span>{profile.phone}</span>
                </div>
                <div className="flex items-center gap-2">
                  <MapPin className="text-gray-400" size={20} />
                  <span>{profile.address}</span>
                </div>
              </div>
            </div>
            <div>
              <h2 className="text-lg font-semibold mb-4">Medical Information</h2>
              <div className="space-y-3">
                <div className="flex items-center gap-2">
                  <span className="font-medium">Blood Group:</span>
                  <span>{profile.bloodGroup}</span>
                </div>
                <div className="flex items-center gap-2">
                  <span className="font-medium">Emergency Contact:</span>
                  <span>{profile.emergencyContact}</span>
                </div>
                <div className="flex items-center gap-2">
                  <span className="font-medium">Allergies:</span>
                  <span>{profile.allergies}</span>
                </div>
                <div className="flex items-center gap-2">
                  <span className="font-medium">Medical Conditions:</span>
                  <span>{profile.medicalConditions}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Blood Donation Status Card */}
        <div className="bg-white rounded-lg shadow-sm p-6 mb-8">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-semibold">Blood Donation Status</h2>
            <div className="flex items-center gap-2">
              <span className="text-sm text-gray-600">Available for donation</span>
              <button
                className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                  bloodDonorStatus ? 'bg-blue-500' : 'bg-gray-300'
                }`}
                onClick={() => setBloodDonorStatus(!bloodDonorStatus)}
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
              <div className="text-blue-500 mb-2">
                <Activity size={24} />
              </div>
              <div className="text-2xl font-bold">{bloodDonationStats.totalDonations}</div>
              <div className="text-sm text-gray-600">Total Donations</div>
            </div>
            <div className="bg-green-50 rounded-lg p-4">
              <div className="text-green-500 mb-2">
                <Calendar size={24} />
              </div>
              <div className="text-2xl font-bold">
                {new Date(bloodDonationStats.lastDonation).toLocaleDateString()}
              </div>
              <div className="text-sm text-gray-600">Last Donation</div>
            </div>
            <div className="bg-red-50 rounded-lg p-4">
              <div className="text-red-500 mb-2">
                <ToggleLeft size={24} />
              </div>
              <div className="text-2xl font-bold">{bloodDonationStats.bloodGroup}</div>
              <div className="text-sm text-gray-600">Blood Group</div>
            </div>
            <div className="bg-purple-50 rounded-lg p-4">
              <div className="text-purple-500 mb-2">
                <Activity size={24} />
              </div>
              <div className="text-2xl font-bold">{bloodDonationStats.livesSaved}</div>
              <div className="text-sm text-gray-600">Lives Saved</div>
            </div>
          </div>
        </div>

        {/* Appointments Section */}
        <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-lg font-semibold mb-6">My Appointments</h2>

          <div className="flex gap-4 mb-6">
            <button
              className={`px-4 py-2 rounded-full ${
                activeTab === 'upcoming'
                  ? 'bg-blue-500 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
              onClick={() => setActiveTab('upcoming')}
            >
              Upcoming
            </button>
            <button
              className={`px-4 py-2 rounded-full ${
                activeTab === 'current'
                  ? 'bg-blue-500 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
              onClick={() => setActiveTab('current')}
            >
              Current
            </button>
            <button
              className={`px-4 py-2 rounded-full ${
                activeTab === 'past'
                  ? 'bg-blue-500 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
              onClick={() => setActiveTab('past')}
            >
              Past
            </button>
          </div>

          <div className="space-y-4">
            {filteredAppointments.length === 0 ? (
              <div className="text-center py-8 text-gray-500">
                No {activeTab} appointments found
              </div>
            ) : (
              filteredAppointments.map((appointment) => (
                <div key={appointment.id} className="border rounded-lg p-4">
                  <div className="flex items-center gap-4">
                    <img
                      src={appointment.doctorImage}
                      alt={appointment.doctorName}
                      className="w-16 h-16 rounded-full object-cover"
                    />
                    <div className="flex-grow">
                      <h3 className="font-semibold">{appointment.doctorName}</h3>
                      <p className="text-blue-500">{appointment.doctorSpecialty}</p>
                      <div className="flex items-center gap-4 mt-2 text-sm text-gray-600">
                        <div className="flex items-center gap-1">
                          <Calendar size={16} />
                          <span>{new Date(appointment.date).toLocaleDateString()}</span>
                        </div>
                        <div className="flex items-center gap-1">
                          <Clock size={16} />
                          <span>{appointment.time}</span>
                        </div>
                      </div>
                    </div>
                    <div className="text-right">
                      <span className={`inline-block px-3 py-1 rounded-full text-sm ${
                        appointment.status === 'completed' ? 'bg-green-100 text-green-600' :
                        appointment.status === 'cancelled' ? 'bg-red-100 text-red-600' :
                        'bg-blue-100 text-blue-600'
                      }`}>
                        {appointment.status.charAt(0).toUpperCase() + appointment.status.slice(1)}
                      </span>
                      {appointment.status === 'completed' && (
                        <div className="mt-2">
                          {appointment.prescriptionUrl && (
                            <Link
                              to={appointment.prescriptionUrl}
                              className="flex items-center gap-1 text-blue-500 hover:text-blue-600"
                            >
                              <Download size={16} />
                              <span>Prescription</span>
                            </Link>
                          )}
                          <div className="flex items-center gap-1 mt-1">
                            <Star size={16} className="text-yellow-500" fill="currentColor" />
                            <span>{appointment.rating}/5</span>
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>

      {/* Edit Profile Modal */}
      {showEditProfile && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-lg p-6 max-w-2xl w-full max-h-[90vh] overflow-y-auto">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-semibold">Edit Profile</h2>
              <button
                onClick={() => setShowEditProfile(false)}
                className="text-gray-500 hover:text-gray-700"
              >
                <X size={24} />
              </button>
            </div>

            <form onSubmit={handleProfileUpdate} className="space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Full Name
                  </label>
                  <input
                    type="text"
                    value={profile.name}
                    onChange={(e) => setProfile({ ...profile, name: e.target.value })}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Email
                  </label>
                  <input
                    type="email"
                    value={profile.email}
                    onChange={(e) => setProfile({ ...profile, email: e.target.value })}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Phone Number
                  </label>
                  <input
                    type="tel"
                    value={profile.phone}
                    onChange={(e) => setProfile({ ...profile, phone: e.target.value })}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Date of Birth
                  </label>
                  <input
                    type="date"
                    value={profile.dateOfBirth}
                    onChange={(e) => setProfile({ ...profile, dateOfBirth: e.target.value })}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Gender
                  </label>
                  <select
                    value={profile.gender}
                    onChange={(e) => setProfile({ ...profile, gender: e.target.value })}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  >
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="other">Other</option>
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Blood Group
                  </label>
                  <select
                    value={profile.bloodGroup}
                    onChange={(e) => setProfile({ ...profile, bloodGroup: e.target.value })}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  >
                    <option value="A+">A+</option>
                    <option value="A-">A-</option>
                    <option value="B+">B+</option>
                    <option value="B-">B-</option>
                    <option value="AB+">AB+</option>
                    <option value="AB-">AB-</option>
                    <option value="O+">O+</option>
                    <option value="O-">O-</option>
                  </select>
                </div>

                <div className="md:col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Address
                  </label>
                  <input
                    type="text"
                    value={profile.address}
                    onChange={(e) => setProfile({ ...profile, address: e.target.value })}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Emergency Contact
                  </label>
                  <input
                    type="tel"
                    value={profile.emergencyContact}
                    onChange={(e) => setProfile({ ...profile, emergencyContact: e.target.value })}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Allergies
                  </label>
                  <input
                    type="text"
                    value={profile.allergies}
                    onChange={(e) => setProfile({ ...profile, allergies: e.target.value })}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  />
                </div>

                <div className="md:col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Medical Conditions
                  </label>
                  <textarea
                    value={profile.medicalConditions}
                    onChange={(e) => setProfile({ ...profile, medicalConditions: e.target.value })}
                    rows={3}
                    className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                  />
                </div>
              </div>

              <div className="flex justify-end gap-4">
                <button
                  type="button"
                  className="px-6 py-2 text-gray-600 hover:text-gray-800"
                  onClick={() => setShowEditProfile(false)}
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-6 py-2 bg-blue-500 text-white rounded-full hover:bg-blue-600"
                >
                  Save Changes
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}