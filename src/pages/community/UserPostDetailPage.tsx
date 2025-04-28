import { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { api } from '../../lib/api';
import { ArrowLeft, Heart, MessageCircle, Share2, Send, MoreVertical, X } from 'lucide-react';
import { toast } from 'react-hot-toast';

interface Comment {
  id: number;
  userId: number;
  userName: string;
  content: string;
}

interface Blog {
  blogId: number;
  userName: string;
  title: string;
  content: string;
  media?: string;
  likeCount: number;
  commentCount: number;
  commentResponseLists?: Comment[];
  createdAt: string;
  category: string;
  doctorCategory: string | null;
  isFeatured: boolean;
}

export function UserPostDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [blog, setBlog] = useState<Blog | null>(null);
  const [comments, setComments] = useState<Comment[]>([]);
  const [newComment, setNewComment] = useState('');
  const [menuOpen, setMenuOpen] = useState(false);
  const [editModalOpen, setEditModalOpen] = useState(false);
  const [editCommentModal, setEditCommentModal] = useState<{ open: boolean; comment: Comment | null }>({ open: false, comment: null });
  const [editTitle, setEditTitle] = useState('');
  const [editContent, setEditContent] = useState('');
  const [editCommentContent, setEditCommentContent] = useState('');

  useEffect(() => {
    if (id) fetchBlogDetails(id);
  }, [id]);

  const fetchBlogDetails = async (blogId: string) => {
    try {
      const { data } = await api.get(`/api/v1/blog/${blogId}`);
      if (data?.code === 'XS0001') {
        setBlog(data.data);
        setComments(data.data.commentResponseLists || []);
      }
    } catch (error) {
      console.error('Error fetching blog:', error);
      toast.error('Failed to load blog.');
    }
  };

  const handleLike = async () => {
    if (!id) return;
    try {
      const token = localStorage.getItem('token');
      if (!token) return toast.error('Login required.');

      const { data } = await api.post(`/api/v1/like/create/blogs/${id}/like`, {}, {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (data?.code === 'XS0001') {
        setBlog((prev) => prev ? { ...prev, likeCount: data.data.likeCount } : prev);
        toast.success('Liked!');
      }
    } catch (error) {
      console.error('Error liking blog:', error);
      toast.error('Failed to like.');
    }
  };

  const handleShare = () => {
    if (!id) return;
    const shareUrl = `${window.location.origin}/community/users/post/${id}`;
    navigator.clipboard.writeText(shareUrl);
    toast.success('Link copied to clipboard!');
  };

  const handlePostComment = async () => {
    if (!id || !newComment.trim()) return;
    try {
      const token = localStorage.getItem('token');
      if (!token) return toast.error('Login required.');

      await api.post(`/api/v1/comment/create/${id}/comments`, { content: newComment }, {
        headers: { Authorization: `Bearer ${token}` }
      });

      setNewComment('');
      fetchBlogDetails(id);
      toast.success('Comment posted.');
    } catch (error) {
      console.error('Error posting comment:', error);
      toast.error('Failed to post comment.');
    }
  };

  const handleEditComment = async () => {
    if (!editCommentModal.comment) return;
    try {
      const token = localStorage.getItem('token');
      if (!token) return toast.error('Login required.');

      await api.put(`/api/v1/comment/comments/${editCommentModal.comment.id}`, {
        content: editCommentContent
      }, {
        headers: { Authorization: `Bearer ${token}` }
      });

      setEditCommentModal({ open: false, comment: null });
      setEditCommentContent('');
      if (id) fetchBlogDetails(id);
      toast.success('Comment updated.');
    } catch (error) {
      console.error('Error editing comment:', error);
      toast.error('Failed to edit comment.');
    }
  };

  const handleDeleteComment = async (commentId: number) => {
    if (!confirm('Delete this comment?')) return;
    try {
      const token = localStorage.getItem('token');
      if (!token) return toast.error('Login required.');

      await api.delete(`/api/v1/comment/comments/${commentId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (id) fetchBlogDetails(id);
      toast.success('Comment deleted.');
    } catch (error) {
      console.error('Error deleting comment:', error);
      toast.error('Failed to delete comment.');
    }
  };

  const handleDeleteBlog = async () => {
    if (!confirm('Delete this blog post?')) return;
    try {
      const token = localStorage.getItem('token');
      if (!token) return toast.error('Login required.');

      await api.delete(`/api/v1/blog/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });

      toast.success('Blog deleted.');
      navigate('/community/users');
    } catch (error) {
      console.error('Error deleting blog:', error);
      toast.error('Failed to delete blog.');
    }
  };

  const handleUpdateBlog = async () => {
    if (!blog) return;
    try {
      const token = localStorage.getItem('token');
      if (!token) return toast.error('Login required.');

      const payload = {
        title: editTitle || blog.title,
        content: editContent || blog.content,
        category: blog.category || 'MEDICAL_TIPS',
        doctorCategory: blog.doctorCategory || '',
        media: blog.media || '',
        isFeatured: blog.isFeatured || false
      };

      await api.put(`/api/v1/blog/${blog.blogId}`, payload, {
        headers: { Authorization: `Bearer ${token}` }
      });

      toast.success('Post updated.');
      setEditModalOpen(false);
      if (id) fetchBlogDetails(id);
    } catch (error) {
      console.error('Error updating blog:', error);
      toast.error('Failed to update blog.');
    }
  };

  if (!blog) return <div className="text-center py-20">Loading...</div>;

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto">
        <div className="flex items-center mb-8">
          <Link to="/community/users" className="flex items-center text-gray-600 hover:text-gray-800">
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back
          </Link>
        </div>

        <div className="bg-white rounded-lg shadow-sm p-6 relative">
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
              <button onClick={() => setMenuOpen(!menuOpen)}>
                <MoreVertical size={20} />
              </button>
              {menuOpen && (
                <div className="absolute right-0 mt-2 w-32 bg-white rounded-lg shadow-lg p-2 z-50">
                  <button onClick={() => {
                    setEditTitle(blog.title);
                    setEditContent(blog.content);
                    setEditModalOpen(true);
                    setMenuOpen(false);
                  }} className="block w-full text-left px-4 py-2 hover:bg-gray-100 rounded">
                    Edit Post
                  </button>
                  <button onClick={handleDeleteBlog} className="block w-full text-left px-4 py-2 text-red-500 hover:bg-gray-100 rounded">
                    Delete Post
                  </button>
                </div>
              )}
            </div>
          </div>

          <h2 className="text-xl font-bold mb-2">{blog.title}</h2>
          <p className="mb-4">{blog.content}</p>

          {blog.media && (
            <img src={blog.media} alt="Post" className="w-full rounded-lg mb-4" />
          )}

          <div className="flex items-center justify-between text-gray-500 border-t border-b py-3">
            <button onClick={handleLike} className="flex items-center gap-1 hover:text-red-500">
              <Heart size={20} />
              <span>{blog.likeCount}</span>
            </button>
            <button className="flex items-center gap-1 hover:text-blue-500">
              <MessageCircle size={20} />
              <span>{blog.commentCount}</span>
            </button>
            <button onClick={handleShare} className="flex items-center gap-1 hover:text-green-500">
              <Share2 size={20} />
              <span>Share</span>
            </button>
          </div>

          {/* Comments */}
          <div className="mt-6 space-y-4">
            <h3 className="font-semibold">Comments</h3>
            {comments.map((comment) => (
              <div key={comment.id} className="flex gap-3">
                <div className="flex-grow bg-gray-50 p-3 rounded-lg">
                  <div className="flex justify-between">
                    <h4 className="font-semibold">{comment.userName}</h4>
                    <div className="flex gap-2">
                      <button onClick={() => {
                        setEditCommentModal({ open: true, comment });
                        setEditCommentContent(comment.content);
                      }} className="text-sm text-blue-500">Edit</button>
                      <button onClick={() => handleDeleteComment(comment.id)} className="text-sm text-red-500">Delete</button>
                    </div>
                  </div>
                  <p>{comment.content}</p>
                </div>
              </div>
            ))}
          </div>

          {/* New Comment */}
          <div className="flex gap-3 mt-6">
            <input
              type="text"
              placeholder="Write a comment..."
              className="flex-grow px-4 py-2 bg-gray-50 rounded-full focus:ring-2 focus:ring-blue-200"
              value={newComment}
              onChange={(e) => setNewComment(e.target.value)}
            />
            <button onClick={handlePostComment} className="text-blue-500 hover:text-blue-600">
              <Send size={20} />
            </button>
          </div>
        </div>
      </div>

      {/* Edit Post Modal */}
      {editModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-lg w-full max-w-md">
            <div className="flex justify-between mb-4">
              <h2 className="text-xl font-bold">Edit Post</h2>
              <button onClick={() => setEditModalOpen(false)}>
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
            <button onClick={handleUpdateBlog} className="w-full bg-blue-500 text-white py-2 rounded">
              Save Changes
            </button>
          </div>
        </div>
      )}

      {/* Edit Comment Modal */}
      {editCommentModal.open && editCommentModal.comment && (
        <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-lg w-full max-w-md">
            <div className="flex justify-between mb-4">
              <h2 className="text-xl font-bold">Edit Comment</h2>
              <button onClick={() => setEditCommentModal({ open: false, comment: null })}>
                <X size={24} />
              </button>
            </div>
            <textarea
              placeholder="Comment"
              className="w-full p-3 mb-4 border rounded min-h-[120px]"
              value={editCommentContent}
              onChange={(e) => setEditCommentContent(e.target.value)}
            />
            <button onClick={handleEditComment} className="w-full bg-blue-500 text-white py-2 rounded">
              Save Changes
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
