import { useState } from 'react';
import { Calendar, Clock, FileText, Video, Users, Phone, Star } from 'lucide-react';
import { Link } from 'react-router-dom';

interface Appointment {
  id: string;
  patientName: string;
  patientAge: number;
  patientGender: string;
  date: string;
  time: string;
  type: 'upcoming' | 'current' | 'past';
  consultationType: 'emergency' | 'specialized' | 'offline';
  status: 'scheduled' | 'completed' | 'cancelled';
  symptoms?: string;
  medicalHistory?: string;
}

const appointments: Appointment[] = [
  {
    id: '1',
    patientName: 'John Doe',
    patientAge: 35,
    patientGender: 'Male',
    date: '2024-03-20',
    time: '10:00 AM',
    type: 'upcoming',
    consultationType: 'specialized',
    status: 'scheduled',
    symptoms: 'Chest pain and shortness of breath',
    medicalHistory: 'Hypertension'
  },
  {
    id: '2',
    patientName: 'Jane Smith',
    patientAge: 28,
    patientGender: 'Female',
    date: '2024-03-15',
    time: '2:30 PM',
    type: 'past',
    consultationType: 'emergency',
    status: 'completed',
    symptoms: 'Severe headache',
    medicalHistory: 'None'
  }
];

export function DoctorDashboardPage() {
  const [activeTab, setActiveTab] = useState<'upcoming' | 'current' | 'past'>('upcoming');
  const [showPrescriptionModal, setShowPrescriptionModal] = useState(false);
  const [selectedAppointment, setSelectedAppointment] = useState<Appointment | null>(null);

  const stats = {
    totalPatients: 150,
    todayAppointments: 8,
    totalConsultations: 450,
    rating: 4.8
  };

  const filteredAppointments = appointments.filter(apt => apt.type === activeTab);

  const handleStartConsultation = (appointment: Appointment) => {
    // Handle starting consultation
    console.log('Starting consultation for:', appointment.id);
  };

  const handleAssignPrescription = (appointment: Appointment) => {
    setSelectedAppointment(appointment);
    setShowPrescriptionModal(true);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-6xl mx-auto">
        <div className="flex items-center justify-between mb-8">
          <h1 className="text-2xl font-bold">Doctor Dashboard</h1>
          <Link
            to="/doctor/availability"
            className="bg-blue-500 text-white px-6 py-2 rounded-full hover:bg-blue-600 transition-colors"
          >
            Set Availability
          </Link>
        </div>

        {/* Stats Section */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
          <div className="bg-blue-50 rounded-lg p-4">
            <div className="text-blue-500 mb-2">
              <Users size={24} />
            </div>
            <div className="text-2xl font-bold">{stats.totalPatients}</div>
            <div className="text-sm text-gray-600">Total Patients</div>
          </div>
          <div className="bg-green-50 rounded-lg p-4">
            <div className="text-green-500 mb-2">
              <Calendar size={24} />
            </div>
            <div className="text-2xl font-bold">{stats.todayAppointments}</div>
            <div className="text-sm text-gray-600">Today's Appointments</div>
          </div>
          <div className="bg-purple-50 rounded-lg p-4">
            <div className="text-purple-500 mb-2">
              <Video size={24} />
            </div>
            <div className="text-2xl font-bold">{stats.totalConsultations}</div>
            <div className="text-sm text-gray-600">Total Consultations</div>
          </div>
          <div className="bg-yellow-50 rounded-lg p-4">
            <div className="text-yellow-500 mb-2">
              <Star size={24} />
            </div>
            <div className="text-2xl font-bold">{stats.rating}</div>
            <div className="text-sm text-gray-600">Average Rating</div>
          </div>
        </div>

        {/* Appointments Section */}
        <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-lg font-semibold mb-6">Appointments</h2>

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
                  <div className="flex flex-col md:flex-row md:items-center gap-4">
                    <div className="flex-grow">
                      <div className="flex items-center gap-2 mb-2">
                        <h3 className="font-semibold">{appointment.patientName}</h3>
                        <span className={`px-3 py-1 rounded-full text-sm ${
                          appointment.consultationType === 'emergency' ? 'bg-red-100 text-red-600' :
                          appointment.consultationType === 'specialized' ? 'bg-blue-100 text-blue-600' :
                          'bg-green-100 text-green-600'
                        }`}>
                          {appointment.consultationType.charAt(0).toUpperCase() + appointment.consultationType.slice(1)}
                        </span>
                      </div>
                      <div className="text-sm text-gray-600 mb-2">
                        {appointment.patientAge} years • {appointment.patientGender}
                      </div>
                      <div className="flex items-center gap-4 text-sm text-gray-600">
                        <div className="flex items-center gap-1">
                          <Calendar size={16} />
                          <span>{new Date(appointment.date).toLocaleDateString()}</span>
                        </div>
                        <div className="flex items-center gap-1">
                          <Clock size={16} />
                          <span>{appointment.time}</span>
                        </div>
                      </div>
                      {appointment.symptoms && (
                        <div className="mt-2 text-sm">
                          <span className="font-medium">Symptoms:</span> {appointment.symptoms}
                        </div>
                      )}
                      {appointment.medicalHistory && (
                        <div className="mt-1 text-sm">
                          <span className="font-medium">Medical History:</span> {appointment.medicalHistory}
                        </div>
                      )}
                    </div>
                    <div className="flex flex-col gap-2">
                      {appointment.type === 'upcoming' && (
                        <button
                          onClick={() => handleStartConsultation(appointment)}
                          className="flex items-center justify-center gap-2 px-4 py-2 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors"
                        >
                          <Video size={16} />
                          Start Consultation
                        </button>
                      )}
                      {appointment.type === 'current' && (
                        <button
                          onClick={() => handleAssignPrescription(appointment)}
                          className="flex items-center justify-center gap-2 px-4 py-2 bg-green-500 text-white rounded-full hover:bg-green-600 transition-colors"
                        >
                          <FileText size={16} />
                          Assign Prescription
                        </button>
                      )}
                    </div>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>

      {/* Prescription Modal */}
      {showPrescriptionModal && selectedAppointment && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-lg p-6 max-w-2xl w-full">
            <h3 className="text-lg font-semibold mb-4">Assign Prescription</h3>
            <div className="mb-4">
              <p className="text-gray-600">Patient: {selectedAppointment.patientName}</p>
              <p className="text-gray-600">Date: {new Date(selectedAppointment.date).toLocaleDateString()}</p>
            </div>
            <textarea
              className="w-full h-48 p-3 border rounded-lg mb-4"
              placeholder="Enter prescription details..."
            />
            <div className="flex justify-end gap-4">
              <button
                className="px-4 py-2 text-gray-600 hover:text-gray-800"
                onClick={() => setShowPrescriptionModal(false)}
              >
                Cancel
              </button>
              <button
                className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
                onClick={() => {
                  // Handle saving prescription
                  setShowPrescriptionModal(false);
                }}
              >
                Save Prescription
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}