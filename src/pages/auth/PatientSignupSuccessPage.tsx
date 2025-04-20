import { Link } from 'react-router-dom';
import { CheckCircle, ArrowRight } from 'lucide-react';

export function PatientSignupSuccessPage() {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full mx-auto">
        <div className="text-center">
          <div className="flex justify-center mb-6">
            <CheckCircle size={64} className="text-green-500" />
          </div>
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Welcome to Xenon Healthcare!</h2>
          <p className="text-lg text-gray-600 mb-8">
            Your patient account has been created successfully.
          </p>
        </div>

        <div className="bg-white py-8 px-4 shadow-sm rounded-lg sm:px-10">
          <div className="space-y-6">
            <div className="bg-green-50 rounded-lg p-6">
              <h3 className="font-semibold mb-2">What's Next?</h3>
              <ul className="space-y-3 text-gray-600">
                <li className="flex items-center gap-2">
                  <ArrowRight size={16} className="text-green-500" />
                  Complete your health profile
                </li>
                <li className="flex items-center gap-2">
                  <ArrowRight size={16} className="text-green-500" />
                  Book your first consultation
                </li>
                <li className="flex items-center gap-2">
                  <ArrowRight size={16} className="text-green-500" />
                  Explore available doctors
                </li>
              </ul>
            </div>

            <div className="flex flex-col gap-4">
              <Link
                to="/patient/dashboard"
                className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
              >
                Go to Dashboard
              </Link>
              <Link
                to="/appointment"
                className="w-full flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
              >
                Book Appointment
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}