import React from 'react';
import { CheckCircle } from 'lucide-react';
import { Link } from 'react-router-dom';

export function MedicineConfirmationPage() {
  return (
    <div className="min-h-[80vh] flex items-center justify-center bg-gray-50">
      <div className="max-w-md w-full mx-4 p-8 bg-white rounded-lg shadow-lg text-center">
        <div className="flex justify-center mb-6">
          <CheckCircle className="w-16 h-16 text-green-500" />
        </div>
        <h1 className="text-2xl font-bold text-gray-900 mb-4">
          Order Confirmed!
        </h1>
        <p className="text-gray-600 mb-6">
          Your medicine order has been successfully placed. You will receive a confirmation email shortly with your order details.
        </p>
        <div className="space-y-4">
          <Link
            to="/medicine"
            className="block w-full py-2 px-4 bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-lg transition duration-150"
          >
            Back to Medicine Store
          </Link>
          <Link
            to="/"
            className="block w-full py-2 px-4 bg-gray-100 hover:bg-gray-200 text-gray-800 font-medium rounded-lg transition duration-150"
          >
            Return to Home
          </Link>
        </div>
      </div>
    </div>
  );
}