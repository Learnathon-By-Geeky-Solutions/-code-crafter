import { CheckCircle } from 'lucide-react';
import { Link, useParams, useLocation } from 'react-router-dom';
import { AppointmentSection } from '../components/features/AppointmentSection';

export function ConfirmationPage() {
  const { type, specialty, id } = useParams<{ type: string; specialty: string; id: string }>();
  const location = useLocation();
  const bookingData = location.state?.bookingData;

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto text-center mb-16">
        <div className="bg-white rounded-lg shadow-sm p-8">
          <div className="flex justify-center mb-6">
            <CheckCircle size={64} className="text-green-500" />
          </div>
          
          <h1 className="text-2xl font-bold mb-4">Consultation Booked Successfully!</h1>
          
          <div className="bg-green-50 rounded-lg p-6 mb-8">
            <p className="text-lg mb-4">
              {type === 'specialized' 
                ? 'Your appointment has been scheduled. The doctor will attend to you at your scheduled time.'
                : 'Your telemedicine consultation has been confirmed. The doctor will call you shortly.'}
            </p>
            <div className="text-left space-y-2">
              <p><strong>Appointment ID:</strong> #{Math.random().toString(36).substr(2, 9).toUpperCase()}</p>
              {type === 'specialized' && bookingData ? (
                <>
                  <p><strong>Date:</strong> {new Date(bookingData.date).toLocaleDateString('en-US', {
                    weekday: 'long',
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric'
                  })}</p>
                  <p><strong>Time:</strong> {bookingData.time}</p>
                </>
              ) : (
                <p><strong>Type:</strong> Emergency Consultation</p>
              )}
            </div>
          </div>

          <div className="space-y-4">
            <p className="text-gray-600">
              Please ensure you have a stable internet connection for the video consultation.
            </p>
            <p className="text-gray-600">
              You will receive a confirmation SMS and email with the consultation details.
            </p>
            {type === 'specialized' && (
              <p className="text-gray-600">
                Please be online 5 minutes before your scheduled appointment time.
              </p>
            )}
          </div>

          <div className="mt-8">
            <Link
              to="/"
              className="inline-block bg-blue-500 text-white px-6 py-2 rounded-full hover:bg-blue-600 transition-colors"
            >
              Return to Home
            </Link>
          </div>
        </div>
      </div>

      <div className="border-t pt-16">
        <h2 className="text-2xl font-bold text-center mb-8">Need More Help?</h2>
        <AppointmentSection />
      </div>
    </div>
  );
}