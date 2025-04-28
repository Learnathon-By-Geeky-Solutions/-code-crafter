import { CheckCircle } from 'lucide-react';
import { Link, useLocation } from 'react-router-dom';

export function BloodDonorConfirmationPage() {
  const location = useLocation();
  const formData = location.state?.formData;

  if (!formData) {
    return (
      <div className="container mx-auto px-4 py-8 text-center">
        <p className="text-gray-600">No registration data found.</p>
        <Link to="/blood-donation/donor" className="text-blue-500 hover:text-blue-600">
          Go back to registration
        </Link>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto text-center">
        <div className="bg-white rounded-lg shadow-sm p-8">
          <div className="flex justify-center mb-6">
            <CheckCircle size={64} className="text-green-500" />
          </div>
          
          <h1 className="text-2xl font-bold mb-4">Thank You for Registering as a Blood Donor!</h1>
          
          <div className="bg-green-50 rounded-lg p-6 mb-8">
            <p className="text-lg mb-4">
              Your registration has been successfully completed. You are now part of our blood donor network.
            </p>
            <div className="text-left space-y-2">
              <p><strong>Donor ID:</strong> #{Math.random().toString(36).substr(2, 9).toUpperCase()}</p>
              <p><strong>Name:</strong> {formData.name}</p>
              <p><strong>Blood Group:</strong> {formData.bloodGroup}</p>
              <p><strong>Contact:</strong> {formData.phone}</p>
            </div>
          </div>

          <div className="space-y-4">
            <p className="text-gray-600">
              We will contact you when there is a blood requirement matching your blood group in your area.
            </p>
            <p className="text-gray-600">
              You can view your donation history and update your availability status anytime from your profile.
            </p>
            <p className="text-gray-600">
              Remember, by donating blood you're helping save lives!
            </p>
          </div>

          <div className="mt-8 space-x-4">
            <Link
              to="/blood-donation/history"
              className="inline-block bg-red-500 text-white px-6 py-2 rounded-full hover:bg-red-600 transition-colors"
            >
              View Donation History
            </Link>
            <Link
              to="/blood-donation"
              className="inline-block bg-gray-100 text-gray-700 px-6 py-2 rounded-full hover:bg-gray-200 transition-colors"
            >
              Return to Blood Donation
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}