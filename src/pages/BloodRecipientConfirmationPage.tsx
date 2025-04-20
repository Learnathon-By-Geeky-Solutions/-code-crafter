import { CheckCircle, AlertCircle } from 'lucide-react';
import { Link, useLocation } from 'react-router-dom';

export function BloodRecipientConfirmationPage() {
  const location = useLocation();
  const formData = location.state?.formData;

  if (!formData) {
    return (
      <div className="container mx-auto px-4 py-8 text-center">
        <p className="text-gray-600">No request data found.</p>
        <Link to="/blood-donation/recipient" className="text-blue-500 hover:text-blue-600">
          Go back to blood request
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
          
          <h1 className="text-2xl font-bold mb-4">Blood Request Submitted Successfully!</h1>
          
          <div className="bg-green-50 rounded-lg p-6 mb-8">
            <p className="text-lg mb-4">
              Your blood request has been registered in our system. We will start searching for donors immediately.
            </p>
            <div className="text-left space-y-2">
              <p><strong>Request ID:</strong> #{Math.random().toString(36).substr(2, 9).toUpperCase()}</p>
              <p><strong>Patient Name:</strong> {formData.patientName}</p>
              <p><strong>Blood Group:</strong> {formData.bloodGroup}</p>
              <p><strong>Units Required:</strong> {formData.units}</p>
              <p><strong>Required Date:</strong> {new Date(formData.requiredDate).toLocaleDateString()}</p>
              <p><strong>Hospital:</strong> {formData.hospital}</p>
            </div>
          </div>

          <div className="bg-yellow-50 rounded-lg p-6 mb-8 flex items-start gap-4">
            <AlertCircle className="text-yellow-500 flex-shrink-0 mt-1" />
            <div className="text-left">
              <h3 className="font-semibold mb-2">Next Steps:</h3>
              <ul className="list-disc list-inside space-y-2 text-gray-600">
                <li>Our team will contact potential donors in your area</li>
                <li>You will receive SMS updates about donor matches</li>
                <li>Keep your phone accessible for coordination</li>
                <li>Contact emergency support if the requirement becomes more urgent</li>
              </ul>
            </div>
          </div>

          <div className="space-y-4">
            <p className="text-gray-600">
              For urgent assistance or to update your request, contact our 24/7 helpline:
              <br />
              <span className="font-semibold">+880 1234-567890</span>
            </p>
          </div>

          <div className="mt-8 space-x-4">
            <Link
              to="/blood-donation/create-post"
              className="inline-block bg-red-500 text-white px-6 py-2 rounded-full hover:bg-red-600 transition-colors"
            >
              Create Public Request
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