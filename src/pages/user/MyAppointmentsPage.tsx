import { useState } from 'react';
import { Calendar, Clock, Video, MapPin, Star, Download, FileText, X } from 'lucide-react';
import { Link } from 'react-router-dom';

interface Appointment {
  id: string;
  doctorName: string;
  doctorSpecialty: string;
  doctorImage: string;
  date: string;
  time: string;
  type: 'upcoming' | 'completed' | 'cancelled';
  mode: 'online' | 'offline';
  location?: string;
  prescriptionUrl?: string;
  rating?: number;
  symptoms?: string;
  notes?: string;
}

export function MyAppointmentsPage() {
  const [activeTab, setActiveTab] = useState<'upcoming' | 'completed' | 'cancelled'>('upcoming');
  const [showRatingModal, setShowRatingModal] = useState(false);
  const [selectedAppointment, setSelectedAppointment] = useState<Appointment | null>(null);
  const [rating, setRating] = useState(0);
  const [feedback, setFeedback] = useState('');

  const appointments: Appointment[] = [
    {
      id: '1',
      doctorName: 'Dr. Sarah Johnson',
      doctorSpecialty: 'Cardiologist',
      doctorImage: 'https://images.unsplash.com/photo-1559839734-2b71ea197ec2?auto=format&fit=crop&w=150&h=150&q=80',
      date: '2024-03-20',
      time: '10:00 AM',
      type: 'upcoming',
      mode: 'online',
      symptoms: 'Chest pain and shortness of breath',
      notes: 'Please bring previous ECG reports'
    },
    {
      id: '2',
      doctorName: 'Dr. Michael Chen',
      doctorSpecialty: 'Neurologist',
      doctorImage: 'https://images.unsplash.com/photo-1537368910025-700350fe46c7?auto=format&fit=crop&w=150&h=150&q=80',
      date: '2024-03-15',
      time: '2:30 PM',
      type: 'completed',
      mode: 'offline',
      location: 'City Hospital, Room 302',
      prescriptionUrl: '/prescriptions/sample.pdf',
      rating: 5
    }
  ];

  const handleRating = (appointmentId: string) => {
    const appointment = appointments.find(apt => apt.id === appointmentId);
    if (appointment) {
      setSelectedAppointment(appointment);
      setShowRatingModal(true);
    }
  };

  const submitRating = () => {
    // Handle rating submission
    setShowRatingModal(false);
    setRating(0);
    setFeedback('');
  };

  const filteredAppointments = appointments.filter(apt => apt.type === activeTab);

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-4xl mx-auto">
        <h1 className="text-2xl font-bold mb-8">My Appointments</h1>

        {/* Tabs */}
        <div className="flex gap-4 mb-8">
          <button
            className={`px-6 py-2 rounded-full ${
              activeTab === 'upcoming'
                ? 'bg-blue-500 text-white'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            }`}
            onClick={() => setActiveTab('upcoming')}
          >
            Upcoming
          </button>
          <button
            className={`px-6 py-2 rounded-full ${
              activeTab === 'completed'
                ? 'bg-blue-500 text-white'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            }`}
            onClick={() => setActiveTab('completed')}
          >
            Completed
          </button>
          <button
            className={`px-6 py-2 rounded-full ${
              activeTab === 'cancelled'
                ? 'bg-blue-500 text-white'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            }`}
            onClick={() => setActiveTab('cancelled')}
          >
            Cancelled
          </button>
        </div>

        {/* Appointments List */}
        <div className="space-y-6">
          {filteredAppointments.length === 0 ? (
            <div className="text-center py-12 bg-gray-50 rounded-lg">
              <p className="text-gray-500">No {activeTab} appointments found</p>
            </div>
          ) : (
            filteredAppointments.map((appointment) => (
              <div key={appointment.id} className="bg-white rounded-lg shadow-sm p-6">
                <div className="flex items-center gap-6">
                  <img
                    src={appointment.doctorImage}
                    alt={appointment.doctorName}
                    className="w-20 h-20 rounded-lg object-cover"
                  />
                  <div className="flex-grow">
                    <div className="flex items-center justify-between mb-2">
                      <div>
                        <h3 className="text-lg font-semibold">{appointment.doctorName}</h3>
                        <p className="text-blue-500">{appointment.doctorSpecialty}</p>
                      </div>
                      <div className={`px-4 py-1 rounded-full text-sm ${
                        appointment.mode === 'online'
                          ? 'bg-green-100 text-green-600'
                          : 'bg-blue-100 text-blue-600'
                      }`}>
                        {appointment.mode === 'online' ? 'Online' : 'Hospital Visit'}
                      </div>
                    </div>
                    
                    <div className="flex items-center gap-6 text-gray-600 text-sm">
                      <div className="flex items-center gap-2">
                        <Calendar size={16} />
                        <span>{new Date(appointment.date).toLocaleDateString()}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Clock size={16} />
                        <span>{appointment.time}</span>
                      </div>
                      {appointment.location && (
                        <div className="flex items-center gap-2">
                          <MapPin size={16} />
                          <span>{appointment.location}</span>
                        </div>
                      )}
                    </div>

                    {appointment.symptoms && (
                      <div className="mt-4 p-3 bg-gray-50 rounded-lg">
                        <p className="text-sm text-gray-600">
                          <span className="font-medium">Symptoms:</span> {appointment.symptoms}
                        </p>
                        {appointment.notes && (
                          <p className="text-sm text-gray-600 mt-1">
                            <span className="font-medium">Notes:</span> {appointment.notes}
                          </p>
                        )}
                      </div>
                    )}

                    {appointment.type === 'completed' && (
                      <div className="flex items-center justify-between mt-4 pt-4 border-t">
                        <div className="flex items-center gap-4">
                          {appointment.prescriptionUrl && (
                            <Link
                              to={appointment.prescriptionUrl}
                              className="flex items-center gap-2 text-blue-500 hover:text-blue-600"
                            >
                              <Download size={16} />
                              <span>Download Prescription</span>
                            </Link>
                          )}
                          {appointment.rating ? (
                            <div className="flex items-center gap-1">
                              <Star size={16} className="text-yellow-500 fill-current" />
                              <span>{appointment.rating}/5</span>
                            </div>
                          ) : (
                            <button
                              onClick={() => handleRating(appointment.id)}
                              className="text-blue-500 hover:text-blue-600"
                            >
                              Rate this consultation
                            </button>
                          )}
                        </div>
                        <button className="text-blue-500 hover:text-blue-600">
                          Book Follow-up
                        </button>
                      </div>
                    )}

                    {appointment.type === 'upcoming' && (
                      <div className="flex items-center justify-between mt-4 pt-4 border-t">
                        <button className="text-red-500 hover:text-red-600">
                          Cancel Appointment
                        </button>
                        {appointment.mode === 'online' && (
                          <button className="flex items-center gap-2 px-6 py-2 bg-green-500 text-white rounded-full hover:bg-green-600">
                            <Video size={16} />
                            Join Consultation
                          </button>
                        )}
                      </div>
                    )}
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </div>

      {/* Rating Modal */}
      {showRatingModal && selectedAppointment && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-lg p-6 max-w-md w-full">
            <div className="flex items-center justify-between mb-6">
              <h3 className="text-lg font-semibold">Rate your consultation</h3>
              <button
                onClick={() => setShowRatingModal(false)}
                className="text-gray-400 hover:text-gray-600"
              >
                <X size={24} />
              </button>
            </div>

            <div className="flex items-center gap-2 mb-6">
              {[1, 2, 3, 4, 5].map((star) => (
                <button
                  key={star}
                  onClick={() => setRating(star)}
                  className={`p-1 rounded-full hover:bg-yellow-50 transition-colors ${
                    rating >= star ? 'text-yellow-500' : 'text-gray-300'
                  }`}
                >
                  <Star size={32} fill={rating >= star ? 'currentColor' : 'none'} />
                </button>
              ))}
            </div>

            <textarea
              placeholder="Share your experience (optional)"
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200 mb-6"
              rows={4}
              value={feedback}
              onChange={(e) => setFeedback(e.target.value)}
            />

            <div className="flex justify-end gap-4">
              <button
                onClick={() => setShowRatingModal(false)}
                className="px-4 py-2 text-gray-600 hover:text-gray-800"
              >
                Cancel
              </button>
              <button
                onClick={submitRating}
                className="px-6 py-2 bg-blue-500 text-white rounded-full hover:bg-blue-600"
              >
                Submit Rating
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}