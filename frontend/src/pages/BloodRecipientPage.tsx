import { useState } from 'react';
import { ArrowLeft } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';

export function BloodRecipientPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    patientName: '',
    bloodGroup: '',
    units: '',
    hospital: '',
    requiredDate: '',
    contactPerson: '',
    phone: '',
    reason: ''
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    navigate('/blood-donation/recipient/confirmation', { state: { formData } });
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
        <h1 className="text-2xl font-bold mb-8">Request Blood</h1>

        <form onSubmit={handleSubmit} className="space-y-6 bg-white p-6 rounded-lg shadow-sm">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Patient Name <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              required
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.patientName}
              onChange={(e) => setFormData({ ...formData, patientName: e.target.value })}
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
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

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Units Required <span className="text-red-500">*</span>
              </label>
              <input
                type="number"
                required
                min="1"
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
                value={formData.units}
                onChange={(e) => setFormData({ ...formData, units: e.target.value })}
              />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Hospital Name <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              required
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.hospital}
              onChange={(e) => setFormData({ ...formData, hospital: e.target.value })}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Required Date <span className="text-red-500">*</span>
            </label>
            <input
              type="date"
              required
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.requiredDate}
              onChange={(e) => setFormData({ ...formData, requiredDate: e.target.value })}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Contact Person Name <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              required
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.contactPerson}
              onChange={(e) => setFormData({ ...formData, contactPerson: e.target.value })}
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
              Reason for Blood Requirement <span className="text-red-500">*</span>
            </label>
            <textarea
              required
              rows={3}
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.reason}
              onChange={(e) => setFormData({ ...formData, reason: e.target.value })}
            />
          </div>

          <button
            type="submit"
            className="w-full py-3 bg-red-500 text-white rounded-full hover:bg-red-600 transition-colors"
          >
            Submit Request
          </button>
        </form>
      </div>
    </div>
  );
}