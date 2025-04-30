import { useState, useEffect } from 'react';
import { ArrowLeft } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import { api } from '../../lib/api';
import { toast } from 'react-hot-toast';

interface UserProfile {
  fname: string;
  lname: string;
}

export function CreateUserPostPage() {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [category, setCategory] = useState('MEDICAL_TIPS');
  const [user, setUser] = useState<UserProfile | null>(null);

  useEffect(() => {
    fetchUser();
  }, []);

  const fetchUser = async () => {
    try {
      const token = localStorage.getItem('token');
      if (!token) return;
      const { data } = await api.get('/api/v1/user', {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (data?.code === 'XS0001') {
        setUser({ fname: data.data.fname, lname: data.data.lname });
      }
    } catch (error) {
      console.error('Error fetching user:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem('token');
      if (!token) {
        toast.error('No token found. Please login again.');
        return;
      }

      const payload = {
        title: title || 'User Post',
        content: content || '',
        category,
        doctorCategory: '',
        media: '',
        isFeatured: false
      };

      const { data } = await api.post('/api/v1/blog/create', payload, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (data?.code === 'XS0001') {
        toast.success('Post shared successfully! Redirecting...');
        setTimeout(() => {
          navigate('/community/users');
        }, 1500);
      } else {
        console.log('Backend error:', data);
        toast.error(data?.message || 'Something went wrong.');
      }
    } catch (error: any) {
      console.error('Error while creating post:', error?.response?.data || error);
      toast.error('Something went wrong while sharing the post.');
    }
  };

  const generateAvatarUrl = (fullName: string) => {
    return `https://ui-avatars.com/api/?name=${encodeURIComponent(fullName)}&background=random`;
  };

  const fullName = user ? `${user.fname} ${user.lname}` : 'User';

  return (
    <div className="min-h-screen flex flex-col justify-center items-center bg-gradient-to-br from-blue-50 via-white to-blue-100 px-4 py-12">
      <div className="max-w-2xl w-full">
        <div className="flex items-center mb-6">
          <Link to="/community/users" className="flex items-center text-gray-600 hover:text-gray-800 transition">
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back
          </Link>
        </div>

        {/* Animated Illustration */}
        

        <div className="bg-white rounded-2xl shadow-lg p-8 transition hover:shadow-2xl">
          <h1 className="text-3xl font-extrabold text-center mb-6 tracking-tight text-blue-600">
            Share Your Thoughts
          </h1>

          {/* User Info */}
          <div className="flex flex-col items-center gap-3 mb-8 animate-fadeIn">
            <img
              src={generateAvatarUrl(fullName)}
              alt="User Avatar"
              className="w-16 h-16 rounded-full object-cover border-4 border-blue-100 shadow"
            />
            <h3 className="text-lg font-semibold">{fullName}</h3>
          </div>

          {/* Form */}
          <form onSubmit={handleSubmit} className="space-y-6 animate-fadeIn delay-200">
            <input
              type="text"
              placeholder="Post title..."
              className="w-full p-4 bg-blue-50 rounded-xl border border-gray-300 focus:ring-2 focus:ring-blue-300 focus:outline-none transition"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />

            <textarea
              placeholder="What's on your mind?"
              className="w-full p-4 min-h-[150px] bg-blue-50 rounded-xl border border-gray-300 focus:ring-2 focus:ring-blue-300 focus:outline-none transition"
              value={content}
              onChange={(e) => setContent(e.target.value)}
              required
            />

            {/* Category Selector */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Category
              </label>
              <select
                value={category}
                onChange={(e) => setCategory(e.target.value)}
                className="w-full p-3 bg-blue-50 rounded-xl border border-gray-300 focus:ring-2 focus:ring-blue-300 focus:outline-none"
              >
                <option value="MEDICAL_TIPS">Medical Tips</option>
                <option value="QUESTION">Question</option>
                <option value="COMMUNITY">Community</option>
              </select>
            </div>

            <button
              type="submit"
              className="w-full py-3 bg-gradient-to-r from-blue-500 to-blue-700 text-white rounded-full hover:opacity-90 transition font-bold tracking-wide"
              disabled={!title.trim() || !content.trim()}
            >
              Post Now 🚀
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
