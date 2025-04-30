import { Link } from 'react-router-dom';
import { Calendar, Video } from 'lucide-react';
import { Button } from '../ui/Button';

export function AppointmentSection() {
  return (
    <div className="bg-gray-50 py-16 px-6 md:px-12">
      <div className="max-w-6xl mx-auto">
        <h2 className="text-3xl font-bold text-center mb-12">Book an Appointment</h2>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          <div className="bg-white p-8 rounded-xl shadow-sm hover:shadow-md transition-shadow">
            <div className="text-blue-500 mb-4">
              <Video size={32} />
            </div>
            <h3 className="text-xl font-semibold mb-2">Online Consultation</h3>
            <p className="text-gray-600 mb-6">
              Connect with our doctors virtually from the comfort of your home. Available 24/7 for emergency consultations.
            </p>
            <Link to="/appointment">
              <Button>Book Online</Button>
            </Link>
          </div>

          <div className="bg-white p-8 rounded-xl shadow-sm hover:shadow-md transition-shadow">
            <div className="text-blue-500 mb-4">
              <Calendar size={32} />
            </div>
            <h3 className="text-xl font-semibold mb-2">Visit Our Clinic</h3>
            <p className="text-gray-600 mb-6">
              Schedule an in-person visit with our healthcare professionals at your preferred location and time.
            </p>
            <Link to="/hospitals">
              <Button variant="secondary">Book Visit</Button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}