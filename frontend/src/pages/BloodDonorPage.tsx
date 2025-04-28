import { useState } from 'react';
import { ArrowLeft } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';

export function BloodDonorPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: '',
    bloodGroup: '',
    age: '',
    weight: '',
    lastDonation: '',
    phone: '',
    address: '',
    available: true
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    navigate('/blood-donation/donor/confirmation', { state: { formData } });
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex items-center mb-8">
        <Link to="/blood-donation" className="flex items-center text-gray-600 hover:text-gray-800">
          <ArrowLeft className="w-5 h-5 mr-2" />
          Back
        </Link>
      </div>

      <div className="max-w-2xl mx-auto">
        <h1 className="text-2xl font-bold mb-8">Register as Blood Donor</h1>

        <form onSubmit={handleSubmit} className="space-y-6 bg-white p-6 rounded-lg shadow-sm">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Full Name <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              required
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Blood Group <span className="text-red-500">*</span>
            </label>
            <select
              required
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.bloodGroup}
              onChange={(e) => setFormData({ ...formData, bloodGroup: e.target.value })}
            >
              <option value="">Select blood group</option>
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

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Age <span className="text-red-500">*</span>
              </label>
              <input
                type="number"
                required
                min="18"
                max="60"
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
                value={formData.age}
                onChange={(e) => setFormData({ ...formData, age: e.target.value })}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Weight (kg) <span className="text-red-500">*</span>
              </label>
              <input
                type="number"
                required
                min="50"
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
                value={formData.weight}
                onChange={(e) => setFormData({ ...formData, weight: e.target.value })}
              />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Last Donation Date
            </label>
            <input
              type="date"
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.lastDonation}
              onChange={(e) => setFormData({ ...formData, lastDonation: e.target.value })}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Phone Number <span className="text-red-500">*</span>
            </label>
            <input
              type="tel"
              required
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.phone}
              onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Address <span className="text-red-500">*</span>
            </label>
            <textarea
              required
              rows={3}
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.address}
              onChange={(e) => setFormData({ ...formData, address: e.target.value })}
            />
          </div>

          <div className="flex items-center">
            <input
              type="checkbox"
              id="available"
              className="rounded border-gray-300 text-red-500 focus:ring-red-200"
              checked={formData.available}
              onChange={(e) => setFormData({ ...formData, available: e.target.checked })}
            />
            <label htmlFor="available" className="ml-2 text-sm text-gray-700">
              I am available for blood donation
            </label>
          </div>

          <button
            type="submit"
            className="w-full py-3 bg-red-500 text-white rounded-full hover:bg-red-600 transition-colors"
          >
            Register as Donor
          </button>
        </form>
      </div>
    </div>
  );
}