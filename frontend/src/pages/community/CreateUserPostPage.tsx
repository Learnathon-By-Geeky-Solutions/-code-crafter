import { useState } from 'react';
import { ArrowLeft, Image as ImageIcon, Smile, X } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';

export function CreateUserPostPage() {
  const navigate = useNavigate();
  const [content, setContent] = useState('');
  const [image, setImage] = useState<File | null>(null);
  const [imagePreview, setImagePreview] = useState<string | null>(null);
  const [feeling, setFeeling] = useState<string | null>(null);

  const feelings = [
    '😊 Happy',
    '😷 Sick',
    '😌 Blessed',
    '🤒 Fever',
    '💪 Strong',
    '😔 Worried'
  ];

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setImage(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagePreview(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Handle post creation
    navigate('/community/users');
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto">
        <div className="flex items-center mb-8">
          <Link to="/community/users" className="flex items-center text-gray-600 hover:text-gray-800">
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back
          </Link>
        </div>

        <div className="bg-white rounded-lg shadow-sm p-6">
          <h1 className="text-2xl font-bold mb-6">Create Post</h1>

          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="flex items-center gap-3 mb-6">
              <img
                src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80"
                alt="Your avatar"
                className="w-12 h-12 rounded-full object-cover"
              />
              <div>
                <h3 className="font-semibold">John Doe</h3>
                {feeling && (
                  <p className="text-sm text-gray-500">
                    feeling {feeling}
                  </p>
                )}
              </div>
            </div>

            <textarea
              placeholder="What's on your mind?"
              className="w-full p-4 min-h-[150px] bg-gray-50 rounded-lg border-none focus:ring-2 focus:ring-blue-200"
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />

            {imagePreview && (
              <div className="relative">
                <img
                  src={imagePreview}
                  alt="Preview"
                  className="w-full rounded-lg"
                />
                <button
                  type="button"
                  className="absolute top-2 right-2 p-1 bg-gray-800 bg-opacity-50 rounded-full text-white hover:bg-opacity-70"
                  onClick={() => {
                    setImage(null);
                    setImagePreview(null);
                  }}
                >
                  <X size={20} />
                </button>
              </div>
            )}

            <div className="flex gap-4">
              <label className="flex items-center gap-2 text-gray-600 hover:text-blue-500 cursor-pointer">
                <ImageIcon size={20} />
                <span>Add Photo</span>
                <input
                  type="file"
                  accept="image/*"
                  className="hidden"
                  onChange={handleImageChange}
                />
              </label>

              <div className="relative group">
                <button
                  type="button"
                  className="flex items-center gap-2 text-gray-600 hover:text-blue-500"
                >
                  <Smile size={20} />
                  <span>Feeling</span>
                </button>

                <div className="hidden group-hover:block absolute top-full left-0 mt-2 w-48 bg-white rounded-lg shadow-lg p-2">
                  {feelings.map((f) => (
                    <button
                      key={f}
                      type="button"
                      className="block w-full text-left px-4 py-2 text-gray-700 hover:bg-blue-50 rounded"
                      onClick={() => setFeeling(f)}
                    >
                      {f}
                    </button>
                  ))}
                </div>
              </div>
            </div>

            <button
              type="submit"
              className="w-full py-3 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors"
              disabled={!content.trim()}
            >
              Post
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}