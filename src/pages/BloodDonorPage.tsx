import { useEffect, useState } from 'react';
import { ArrowLeft } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import { api } from '../lib/api';
import { toast } from 'react-hot-toast';

const reverseBloodMap: Record<string, string> = {
  A_POS: 'A+',
  A_NEG: 'A-',
  B_POS: 'B+',
  B_NEG: 'B-',
  AB_POS: 'AB+',
  AB_NEG: 'AB-',
  O_POS: 'O+',
  O_NEG: 'O-',
};

const forwardBloodMap: Record<string, string> = {
  'A+': 'A_POS',
  'A-': 'A_NEG',
  'B+': 'B_POS',
  'B-': 'B_NEG',
  'AB+': 'AB_POS',
  'AB-': 'AB_NEG',
  'O+': 'O_POS',
  'O-': 'O_NEG',
};

export function BloodDonorPage() {
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [profileExists, setProfileExists] = useState(false);

  const [formData, setFormData] = useState({
    bloodGroup: '',
    age: '',
    weight: '',
    interested: true
  });

  // 🔄 Fetch profile on mount
  useEffect(() => {
    const fetchDonorProfile = async () => {
      try {
        const { data: baseResponse } = await api.get('/api/v1/donor/profile');
        if (baseResponse.code === 'XS0001') {
          const d = baseResponse.data;
          setFormData({
            bloodGroup: reverseBloodMap[d.bloodType] || '',
            age: d.age.toString(),
            weight: d.weight.toString(),
            interested: d.interested === 'YES'
          });
          setProfileExists(true); // ✅ Disable submit button
        }
      } catch (err) {
        console.warn('No donor profile found or failed to fetch');
        setProfileExists(false);
      }
    };

    fetchDonorProfile();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!forwardBloodMap[formData.bloodGroup]) {
      toast.error('Invalid blood group selected.');
      return;
    }

    try {
      setIsSubmitting(true);

      const payload = {
        bloodType: forwardBloodMap[formData.bloodGroup],
        age: parseInt(formData.age),
        weight: parseInt(formData.weight),
        interested: formData.interested ? 'YES' : 'NO'
      };

      const { data: baseResponse } = await api.post('/api/v1/donor/create', payload);

      if (baseResponse.code === 'XS0001') {
        toast.success('You have been registered as a blood donor! 🎉', { duration: 2500 });
        setTimeout(() => {
          navigate('/blood-donation/donors');
        }, 2500);
      } else {
        toast.error(baseResponse.message || 'Failed to register.');
      }
    } catch (error: any) {
      console.error('Registration error:', error);
      if (error.response) {
        toast.error(error.response.data.message || 'Something went wrong!');
      } else {
        toast.error('Network error or server unreachable.');
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex items-center mb-8">
        <Link to="/" className="flex items-center text-gray-600 hover:text-gray-800">
          <ArrowLeft className="w-5 h-5 mr-2" />
          Back to Home
        </Link>
      </div>

      <div className="max-w-2xl mx-auto">
        <h1 className="text-2xl font-bold mb-8">Register as a Blood Donor</h1>

        <form onSubmit={handleSubmit} className="space-y-6 bg-white p-6 rounded-lg shadow-sm">
          {/* Blood Group */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Blood Group <span className="text-red-500">*</span>
            </label>
            <select
              required
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.bloodGroup}
              onChange={(e) => setFormData({ ...formData, bloodGroup: e.target.value })}
              disabled={profileExists}
            >
              <option value="">Select blood group</option>
              {Object.keys(forwardBloodMap).map((group) => (
                <option key={group} value={group}>{group}</option>
              ))}
            </select>
          </div>

          {/* Age and Weight */}
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Age <span className="text-red-500">*</span>
              </label>
              <input
                type="number"
                required
                min="18"
                max="65"
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
                value={formData.age}
                onChange={(e) => setFormData({ ...formData, age: e.target.value })}
                disabled={profileExists}
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
                disabled={profileExists}
              />
            </div>
          </div>

          {/* Interested Checkbox */}
          <div className="flex items-center">
            <input
              type="checkbox"
              id="available"
              className="rounded border-gray-300 text-red-500 focus:ring-red-200"
              checked={formData.interested}
              onChange={(e) => setFormData({ ...formData, interested: e.target.checked })}
              disabled={profileExists}
            />
            <label htmlFor="available" className="ml-2 text-sm text-gray-700">
              I am interested to donate blood
            </label>
          </div>

          {/* Submit */}
          <button
            type="submit"
            disabled={isSubmitting || profileExists}
            className="w-full py-3 flex justify-center items-center bg-red-500 text-white rounded-full hover:bg-red-600 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {isSubmitting ? (
              <svg
                className="animate-spin h-5 w-5 mr-2 text-white"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
              >
                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v4l3-3-3-3v4a8 8 0 11-8 8h4z" />
              </svg>
            ) : null}
            {profileExists ? 'Already Registered' : isSubmitting ? 'Submitting...' : 'Register as Donor'}
          </button>
        </form>
      </div>
    </div>
  );
}
