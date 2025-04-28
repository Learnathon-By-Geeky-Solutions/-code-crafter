import { useState } from 'react';
import { ArrowLeft, Image as ImageIcon } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';

export function CreateBloodRequestPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    title: '',
    bloodGroup: '',
    units: '',
    urgency: 'normal',
    location: '',
    contactName: '',
    phone: '',
    description: '',
    image: null as File | null
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    navigate('/blood-donation/create-post/confirmation', { state: { formData } });
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
        <h1 className="text-2xl font-bold mb-8">Create Blood Request Post</h1>

        <form onSubmit={handleSubmit} className="space-y-6 bg-white p-6 rounded-lg shadow-sm">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Post Title <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              required
              placeholder="e.g., Urgent A+ blood needed in City Hospital"
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.title}
              onChange={(e) => setFormData({ ...formData, title: e.target.value })}
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
                Units Needed <span className="text-red-500">*</span>
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
              Urgency Level <span className="text-red-500">*</span>
            </label>
            <select
              required
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.urgency}
              onChange={(e) => setFormData({ ...formData, urgency: e.target.value })}
            >
              <option value="normal">Normal</option>
              <option value="urgent">Urgent</option>
              <option value="emergency">Emergency</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Location <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              required
              placeholder="Hospital or area name"
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.location}
              onChange={(e) => setFormData({ ...formData, location: e.target.value })}
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
              value={formData.contactName}
              onChange={(e) => setFormData({ ...formData, contactName: e.target.value })}
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
              Description <span className="text-red-500">*</span>
            </label>
            <textarea
              required
              rows={4}
              placeholder="Provide additional details about the requirement"
              className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-red-200"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Add Image
            </label>
            <label 
              htmlFor="image-upload" 
              className="border-2 border-dashed border-gray-300 rounded-lg p-8 flex flex-col items-center justify-center cursor-pointer hover:border-red-300 transition-colors"
            >
              <ImageIcon size={32} className="text-gray-400 mb-4" />
              <p className="text-sm text-gray-500 mb-1">Click to upload or drag and drop</p>
              <p className="text-xs text-gray-400">JPG/PNG Format Only (Max 5MB)</p>
              <input
                id="image-upload"
                type="file"
                className="hidden"
                accept=".jpg,.jpeg,.png"
                onChange={(e) => setFormData({ ...formData, image: e.target.files?.[0] || null })}
              />
            </label>
            {formData.image && (
              <p className="mt-2 text-sm text-green-600">
                File selected: {formData.image.name}
              </p>
            )}
          </div>

          <button
            type="submit"
            className="w-full py-3 bg-red-500 text-white rounded-full hover:bg-red-600 transition-colors"
          >
            Create Post
          </button>
        </form>
      </div>
    </div>
  );
}