import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Heart, MessageCircle, Share2, MoreVertical, Filter, X } from 'lucide-react';
import { api } from '../../lib/api';
import { toast } from 'react-hot-toast';

interface Comment {
  id: number;
  userId: number;
  userName: string;
  content: string;
}

interface Blog {
  blogId: number;
  userId: number;
  userName: string;
  title: string;
  content: string;
  category: string;
  media?: string;
  likeCount: number;
  commentCount: number;
  commentResponseLists?: Comment[];
  createdAt: string;
}

export function UserCommunityPage() {
  const [blogs, setBlogs] = useState<Blog[]>([]);
  const [userName, setUserName] = useState('');
  const [loading, setLoading] = useState(true);
  const [selectedCategory, setSelectedCategory] = useState<string>('All');
  const [menuOpenId, setMenuOpenId] = useState<number | null>(null);
  const [editModal, setEditModal] = useState<{ open: boolean; blog: Blog | null }>({ open: false, blog: null });
  const [editTitle, setEditTitle] = useState('');
  const [editContent, setEditContent] = useState('');

  const availableCategories = ['All', 'MEDICAL_TIPS', 'QUESTION', 'COMMUNITY'];

  useEffect(() => {
    fetchBlogs();
    fetchUser();
  }, []);

  const fetchBlogs = async () => {
    try {
      const { data } = await api.get('/api/v1/blog', {
        params: { page: 0, size: 100, sortBy: 'createdAt', direction: 'desc' }
      });
      if (data?.code === 'XS0001') {
        setBlogs(data.data.content);
      }
    } catch (error) {
      console.error('Error fetching blogs:', error);
      toast.error('Failed to load community feed.');
    } finally {
      setLoading(false);
    }
  };

  const fetchUser = async () => {
    try {
      const token = localStorage.getItem('token');
      if (!token) return;
      const { data } = await api.get('/api/v1/user', {
        headers: { Authorization: `Bearer ${token}` }
      });
      if (data?.code === 'XS0001') {
        setUserName(`${data.data.fname} ${data.data.lname}`);
      }
    } catch (error) {
      console.error('Error fetching user:', error);
    }
  };

  const filteredBlogs = selectedCategory === 'All'
    ? blogs
    : blogs.filter((blog) => blog.category === selectedCategory);

  const handleLike = async (blogId: number) => {
    try {
      const token = localStorage.getItem('token');
      if (!token) return toast.error('Login required to like.');

      const { data } = await api.post(`/api/v1/like/create/blogs/${blogId}/like`, {}, {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (data?.code === 'XS0001') {
        setBlogs((prev) =>
          prev.map((blog) =>
            blog.blogId === blogId
              ? { ...blog, likeCount: data.data.likeCount }
              : blog
          )
        );
      } else {
        toast.error(data?.message || 'Failed to like post.');
      }
    } catch (error) {
      console.error('Error liking blog:', error);
      toast.error('Failed to like.');
    }
  };

  const handleDelete = async (blogId: number) => {
    if (!confirm('Are you sure you want to delete this post?')) return;
    try {
      const token = localStorage.getItem('token');
      if (!token) return toast.error('Login required to delete.');

      await api.delete(`/api/v1/blog/${blogId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });

      setBlogs((prev) => prev.filter((blog) => blog.blogId !== blogId));
      toast.success('Post deleted successfully.');
    } catch (error) {
      console.error('Error deleting blog:', error);
      toast.error('Failed to delete post.');
    }
  };

  const handleShare = (blogId: number) => {
    const shareUrl = `${window.location.origin}/community/users/post/${blogId}`;
    navigator.clipboard.writeText(shareUrl);
    toast.success('Blog link copied to clipboard!');
  };

  const toggleMenu = (blogId: number) => {
    setMenuOpenId(menuOpenId === blogId ? null : blogId);
  };

  const openEditModal = (blog: Blog) => {
    setEditModal({ open: true, blog });
    setEditTitle(blog.title);
    setEditContent(blog.content);
    setMenuOpenId(null);
  };

  const handleUpdatePost = async () => {
    if (!editModal.blog) return;
    try {
      const token = localStorage.getItem('token');
      if (!token) return toast.error('Login required.');

      const payload = {
        title: editTitle,
        content: editContent,
        category: editModal.blog.category || 'MEDICAL_TIPS'
      };

      const { data } = await api.put(`/api/v1/blog/${editModal.blog.blogId}`, payload, {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (data?.code === 'XS0001') {
        toast.success('Post updated successfully.');
        fetchBlogs();
        setEditModal({ open: false, blog: null });
      } else {
        toast.error(data?.message || 'Failed to update post.');
      }
    } catch (error) {
      console.error('Error updating blog:', error);
      toast.error('Failed to update post.');
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-5xl mx-auto">
        {/* Top Bar */}
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-2xl font-bold">Howdy, {userName || 'User'}</h1>
          <div className="flex items-center gap-4">
            <div className="relative">
              <select
                className="appearance-none bg-white border border-gray-200 rounded-full px-4 py-2 pr-8 hover:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-200"
                value={selectedCategory}
                onChange={(e) => setSelectedCategory(e.target.value)}
              >
                {availableCategories.map((cat) => (
                  <option key={cat} value={cat}>
                    {cat === 'All' ? 'All Posts' : cat.replace('_', ' ')}
                  </option>
                ))}
              </select>
              <Filter className="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-400 pointer-events-none" size={16} />
            </div>
            <Link
              to="/community/users/create"
              className="bg-blue-500 text-white px-6 py-2 rounded-full hover:bg-blue-600 transition-colors"
            >
              Create Post
            </Link>
          </div>
        </div>

        {/* Blog List */}
        {loading ? (
          <div className="text-center py-20">Loading feed...</div>
        ) : (
          <div className="space-y-6">
            {filteredBlogs.map((blog) => (
              <div key={blog.blogId} className="bg-white rounded-lg shadow-sm hover:shadow-md transition-shadow p-6 relative">
                {/* Avatar, Name, Menu */}
                <div className="flex items-center gap-3 mb-4">
                  <img
                    src={`https://ui-avatars.com/api/?name=${encodeURIComponent(blog.userName)}&background=random`}
                    alt={blog.userName}
                    className="w-10 h-10 rounded-full object-cover"
                  />
                  <div className="flex-grow">
                    <h3 className="font-semibold">{blog.userName}</h3>
                    <p className="text-sm text-gray-500">{new Date(blog.createdAt).toLocaleDateString()}</p>
                  </div>
                  <div className="relative">
                    <button onClick={() => toggleMenu(blog.blogId)}>
                      <MoreVertical size={20} className="text-gray-600" />
                    </button>
                    {menuOpenId === blog.blogId && (
                      <div className="absolute right-0 mt-2 w-28 bg-white rounded-lg shadow-lg p-2 z-50">
                        <button
                          onClick={() => openEditModal(blog)}
                          className="block w-full text-left px-4 py-2 text-gray-700 hover:bg-gray-100 rounded"
                        >
                          Edit
                        </button>
                        <button
                          onClick={() => handleDelete(blog.blogId)}
                          className="block w-full text-left px-4 py-2 text-red-500 hover:bg-gray-100 rounded"
                        >
                          Delete
                        </button>
                      </div>
                    )}
                  </div>
                </div>

                {/* Title, Content, Category */}
                <h2 className="text-lg font-bold mb-1">{blog.title}</h2>
                <span className="inline-block mb-2 text-xs px-2 py-1 rounded-full bg-blue-100 text-blue-600">
                  {blog.category.replace('_', ' ')}
                </span>
                <p className="text-gray-700 mb-4">{blog.content}</p>

                {blog.media && (
                  <img
                    src={blog.media}
                    alt="Post content"
                    className="w-full rounded-lg mb-4 hover:opacity-95 transition-opacity"
                  />
                )}

                {/* Actions */}
                <div className="flex items-center justify-between text-gray-500 border-t border-b py-3">
                  <button onClick={() => handleLike(blog.blogId)} className="flex items-center gap-1 hover:text-red-500">
                    <Heart size={20} />
                    <span>{blog.likeCount}</span>
                  </button>

                  <Link to={`/community/users/post/${blog.blogId}`} className="flex items-center gap-1 hover:text-blue-500">
                    <MessageCircle size={20} />
                    <span>{blog.commentCount}</span>
                  </Link>

                  <button onClick={() => handleShare(blog.blogId)} className="flex items-center gap-1 hover:text-green-500">
                    <Share2 size={20} />
                    <span>Share</span>
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      {/* Edit Modal */}
      {editModal.open && editModal.blog && (
        <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-lg w-full max-w-md">
            <div className="flex justify-between mb-4">
              <h2 className="text-xl font-bold">Edit Post</h2>
              <button onClick={() => setEditModal({ open: false, blog: null })}>
                <X size={24} />
              </button>
            </div>

            <input
              type="text"
              placeholder="Title"
              className="w-full p-3 mb-4 border rounded"
              value={editTitle}
              onChange={(e) => setEditTitle(e.target.value)}
            />

            <textarea
              placeholder="Content"
              className="w-full p-3 mb-4 border rounded min-h-[120px]"
              value={editContent}
              onChange={(e) => setEditContent(e.target.value)}
            />

            <button
              onClick={handleUpdatePost}
              className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600"
            >
              Save Changes
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
