import { useEffect, useState } from 'react';
import { ArrowLeft, X } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import { api } from '../../lib/api';
import { toast } from 'react-hot-toast';

interface UserProfile {
  fname: string;
  lname: string;
}

export function CreateDoctorArticlePage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    title: '',
    content: '',
    tags: [] as string[],
    doctorCategory: '' // ✅ Added doctorCategory here
  });
  const [newTag, setNewTag] = useState('');
  const [user, setUser] = useState<UserProfile | null>(null);

  const allowedCategories = ['MEDICAL_TIPS', 'HEALTHCARE', 'WELLNESS'];

  useEffect(() => {
    fetchUser();
  }, []);

  const fetchUser = async () => {
    try {
      const token = localStorage.getItem('token');
      if (!token) return;

      const { data } = await api.get('/api/v1/user', {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (data?.code === 'XS0001') {
        setUser({
          fname: data.data.fname,
          lname: data.data.lname
        });
      }
    } catch (error) {
      console.error('Error fetching user:', error);
    }
  };

  const handleAddTag = () => {
    if (newTag.trim() && !formData.tags.includes(newTag.trim())) {
      setFormData({
        ...formData,
        tags: [...formData.tags, newTag.trim()]
      });
      setNewTag('');
    }
  };

  const handleRemoveTag = (tagToRemove: string) => {
    setFormData({
      ...formData,
      tags: formData.tags.filter(tag => tag !== tagToRemove)
    });
  };

  const generateAvatarUrl = (fullName: string) => {
    return `https://ui-avatars.com/api/?name=${encodeURIComponent(fullName)}&background=random`;
  };

  const fullName = user ? `${user.fname} ${user.lname}` : 'Doctor';

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem('token');
      if (!token) {
        toast.error('Please login first.');
        return;
      }

      let selectedCategory = 'MEDICAL_TIPS';
      if (formData.tags.length > 0) {
        const firstTag = formData.tags[0].toUpperCase().replace(' ', '_');
        if (allowedCategories.includes(firstTag)) {
          selectedCategory = firstTag;
        }
      }

      const payload = {
        title: formData.title,
        content: formData.content,
        category: selectedCategory,
        doctorCategory: formData.doctorCategory || '', // ✅ Send doctorCategory here too
        media: '',
        isFeatured: false
      };

      const { data } = await api.post('/api/v1/doctor/articles', payload, {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (data?.code === 'XS0001') {
        toast.success('Article published successfully!');
        navigate('/community/doctors');
      } else {
        toast.error(data?.message || 'Failed to create article.');
      }
    } catch (error: any) {
      console.error('Error creating article:', error?.response?.data || error);
      toast.error('Failed to publish article.');
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-3xl mx-auto">
        {/* Top Bar */}
        <div className="flex items-center mb-8">
          <Link to="/community/doctors" className="flex items-center text-gray-600 hover:text-gray-800">
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back
          </Link>
        </div>

        {/* Form Card */}
        <div className="bg-white rounded-lg shadow-sm p-6">
          {/* Doctor Info */}
          <div className="flex items-center gap-3 mb-6">
            <img
              src={generateAvatarUrl(fullName)}
              alt="Doctor Avatar"
              className="w-12 h-12 rounded-full object-cover"
            />
            <div>
              <h3 className="font-semibold">{fullName}</h3>
              <p className="text-sm text-gray-500">Doctor</p>
            </div>
          </div>

          {/* Form */}
          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Title */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Article Title <span className="text-red-500">*</span>
              </label>
              <input
                type="text"
                required
                value={formData.title}
                onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                placeholder="Enter a descriptive title"
                className="w-full p-3 bg-gray-50 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-200"
              />
            </div>

            {/* Content */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Content <span className="text-red-500">*</span>
              </label>
              <textarea
                required
                rows={12}
                value={formData.content}
                onChange={(e) => setFormData({ ...formData, content: e.target.value })}
                placeholder="Write your article content here..."
                className="w-full p-4 bg-gray-50 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-200"
              />
            </div>

            {/* Doctor Category */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Doctor Category (optional)
              </label>
              <input
                type="text"
                value={formData.doctorCategory}
                onChange={(e) => setFormData({ ...formData, doctorCategory: e.target.value })}
                placeholder="Enter doctor category if any"
                className="w-full p-3 bg-gray-50 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-200"
              />
            </div>

            {/* Categories */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Categories
              </label>
              <div className="flex flex-wrap gap-2 mb-3">
                {formData.tags.map((tag) => (
                  <span
                    key={tag}
                    className="px-3 py-1 bg-blue-50 text-blue-600 rounded-full text-sm flex items-center gap-1"
                  >
                    {tag}
                    <button
                      type="button"
                      onClick={() => handleRemoveTag(tag)}
                      className="hover:text-blue-800"
                    >
                      <X size={14} />
                    </button>
                  </span>
                ))}
              </div>
              <div className="flex gap-2">
                <input
                  type="text"
                  className="flex-grow p-3 bg-gray-50 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-200"
                  value={newTag}
                  onChange={(e) => setNewTag(e.target.value)}
                  placeholder="Add a category"
                  onKeyDown={(e) => e.key === 'Enter' && (e.preventDefault(), handleAddTag())}
                />
                <button
                  type="button"
                  onClick={handleAddTag}
                  className="px-6 py-3 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200"
                >
                  Add
                </button>
              </div>
            </div>

            {/* Buttons */}
            <div className="flex gap-4">
              <button
                type="button"
                onClick={() => navigate('/community/doctors')}
                className="flex-1 py-3 bg-gray-100 text-gray-700 rounded-full hover:bg-gray-200"
              >
                Cancel
              </button>
              <button
                type="submit"
                className="flex-1 py-3 bg-blue-500 text-white rounded-full hover:bg-blue-600"
              >
                Publish Article
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
