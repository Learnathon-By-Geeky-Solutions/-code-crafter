import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { api } from '../../lib/api';
import { Bookmark, Heart, MessageCircle, MoreVertical, Share2, Trash2, Edit } from 'lucide-react';
import { toast } from 'react-hot-toast';

interface Article {
  id: number;
  title: string;
  content: string;
  media: string;
  category: string;
  doctorCategory: string | null;
  isFeatured: boolean;
  createdAt: string;
  updatedAt: string;
  likeCount: number;
  commentCount: number;
  viewCount: number;
  userName: string;
}

export function DoctorCommunityPage() {
  const [articles, setArticles] = useState<Article[]>([]);
  const [refresh, setRefresh] = useState(false);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchArticles() {
      try {
        setLoading(true);
        const res = await api.get('/api/v1/doctor/articles', {
          params: {
            page: 0,
            size: 10,
            sortBy: 'createdAt',
            direction: 'desc',
          },
        });

        const content = res?.data?.body?.data?.content ?? [];
        setArticles(content);
      } catch (error) {
        console.error('Error fetching articles:', error);
        toast.error('Failed to load articles');
      } finally {
        setLoading(false);
      }
    }

    fetchArticles();
  }, [refresh]);

  const handleLike = async (id: number) => {
    try {
      await api.post(`/api/v1/doctor/articles/${id}/like`);
      toast.success('Liked!');
      setRefresh(prev => !prev);
    } catch {
      toast.error('Failed to like');
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Are you sure you want to delete this article?')) return;
    try {
      await api.delete(`/api/v1/doctor/articles/${id}`);
      toast.success('Deleted!');
      setRefresh(prev => !prev);
    } catch {
      toast.error('Failed to delete');
    }
  };

  const handleShare = (id: number) => {
    navigator.clipboard.writeText(`${window.location.origin}/community/doctors/article/${id}`);
    toast.success('Link copied!');
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-4xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-2xl font-bold">Medical Articles</h1>
          <Link
            to="/community/doctors/create"
            className="bg-blue-500 text-white px-6 py-2 rounded-full hover:bg-blue-600"
          >
            Write Article
          </Link>
        </div>

        {loading ? (
          <p>Loading articles...</p>
        ) : articles.length === 0 ? (
          <p className="text-gray-500 text-center">No articles found.</p>
        ) : (
          <div className="space-y-8">
            {articles.map((article) => (
              <div key={article.id} className="bg-white rounded-lg shadow-sm overflow-hidden relative">
                <img
                  src={article.media || '/default-cover.jpg'}
                  onError={(e) => (e.currentTarget.src = '/default-cover.jpg')}
                  alt={article.title}
                  className="w-full h-64 object-cover"
                />

                <div className="p-6">
                  <div className="flex items-center justify-between mb-4">
                    <div>
                      <h3 className="font-semibold">{article.userName}</h3>
                      <p className="text-sm text-gray-500">{new Date(article.createdAt).toLocaleDateString()}</p>
                    </div>
                    <MoreVertical className="cursor-pointer" />
                  </div>

                  <Link to={`/community/doctors/article/${article.id}`}>
                    <h2 className="text-2xl font-bold mb-3 hover:text-blue-600">
                      {article.title}
                    </h2>
                  </Link>

                  <div className="flex flex-wrap gap-2 mb-4">
                    <span className="px-3 py-1 bg-blue-50 text-blue-600 rounded-full text-sm">
                      {article.category}
                    </span>
                  </div>

                  <div className="flex items-center justify-between text-gray-500 border-t pt-4">
                    <div className="flex items-center gap-6">
                      <button onClick={() => handleLike(article.id)} className="flex items-center gap-1 hover:text-red-500">
                        <Heart size={20} />
                        <span>{article.likeCount}</span>
                      </button>
                      <Link to={`/community/doctors/article/${article.id}`} className="flex items-center gap-1 hover:text-blue-500">
                        <MessageCircle size={20} />
                        <span>{article.commentCount}</span>
                      </Link>
                      <button onClick={() => handleShare(article.id)} className="flex items-center gap-1 hover:text-green-500">
                        <Share2 size={20} />
                        <span>Share</span>
                      </button>
                    </div>
                    <div className="flex gap-4">
                      <button onClick={() => navigate(`/community/doctors/edit/${article.id}`)} className="hover:text-blue-500">
                        <Edit size={20} />
                      </button>
                      <button onClick={() => handleDelete(article.id)} className="hover:text-red-500">
                        <Trash2 size={20} />
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
