import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../../lib/api';
import { toast } from 'react-hot-toast';

export function CreateArticlePage() {
  const [form, setForm] = useState({
    title: '',
    content: '',
    media: '',
    category: '',
    doctorCategory: '',
  });

  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!form.title || !form.content || !form.category) {
      toast.error('Please fill required fields');
      return;
    }

    try {
      setLoading(true);
      await api.post(`/api/v1/doctor/articles/create?category=${form.category}`, {
        title: form.title,
        content: form.content,
        doctorCategory: form.doctorCategory,
        media: form.media,
        category: form.category,
      });

      toast.success('Article created!');
      navigate('/community/doctors');
    } catch (error) {
      console.error(error);
      toast.error('Failed to create article');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-6">
      <h1 className="text-2xl font-bold mb-6">Write New Article</h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          type="text"
          name="title"
          placeholder="Title"
          value={form.title}
          onChange={handleChange}
          className="w-full p-2 border rounded"
        />
        <textarea
          name="content"
          placeholder="Content"
          value={form.content}
          onChange={handleChange}
          className="w-full p-2 border rounded h-40"
        />
        <input
          type="text"
          name="media"
          placeholder="Media URL"
          value={form.media}
          onChange={handleChange}
          className="w-full p-2 border rounded"
        />
        <input
          type="text"
          name="category"
          placeholder="Category"
          value={form.category}
          onChange={handleChange}
          className="w-full p-2 border rounded"
        />
        <input
          type="text"
          name="doctorCategory"
          placeholder="Doctor Category (optional)"
          value={form.doctorCategory}
          onChange={handleChange}
          className="w-full p-2 border rounded"
        />
        <button
          type="submit"
          disabled={loading}
          className="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600"
        >
          {loading ? 'Submitting...' : 'Publish Article'}
        </button>
      </form>
    </div>
  );
}
