import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Heart, MessageCircle, Share2, Image as ImageIcon, Smile, Send, TrendingUp, Filter } from 'lucide-react';

interface Post {
  id: string;
  user: {
    name: string;
    avatar: string;
    badge?: string;
  };
  content: string;
  image?: string;
  likes: number;
  comments: number;
  shares: number;
  timeAgo: string;
  tags?: string[];
}

const posts: Post[] = [
  {
    id: '1',
    user: {
      name: 'Sarah Johnson',
      avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80',
      badge: 'Top Contributor'
    },
    content: 'Just had an amazing experience with Dr. Smith at City Hospital. The new telemedicine service is so convenient! 🏥 #HealthcareTechnology',
    image: 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=800&q=80',
    likes: 124,
    comments: 18,
    shares: 5,
    timeAgo: '2 hours ago',
    tags: ['Healthcare', 'Telemedicine']
  },
  {
    id: '2',
    user: {
      name: 'Michael Chen',
      avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=150&h=150&q=80'
    },
    content: 'Grateful for the quick response from the blood donation community yesterday. My father received the required blood type within hours. This platform is literally saving lives! ❤️ #BloodDonation #Community',
    likes: 256,
    comments: 42,
    shares: 15,
    timeAgo: '5 hours ago',
    tags: ['BloodDonation', 'Healthcare']
  }
];

const trendingTopics = [
  { name: 'Healthcare', posts: 1234 },
  { name: 'BloodDonation', posts: 856 },
  { name: 'MentalHealth', posts: 743 },
  { name: 'Wellness', posts: 521 },
  { name: 'COVID19', posts: 432 }
];

export function UserCommunityPage() {
  const [newComment, setNewComment] = useState('');
  const [selectedFilter, setSelectedFilter] = useState('trending');

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-5xl mx-auto">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {/* Left Sidebar */}
          <div className="md:col-span-2">
            <div className="flex justify-between items-center mb-8">
              <h1 className="text-2xl font-bold">Community Feed</h1>
              <div className="flex items-center gap-4">
                <div className="relative">
                  <select
                    className="appearance-none bg-white border border-gray-200 rounded-full px-4 py-2 pr-8 hover:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-200"
                    value={selectedFilter}
                    onChange={(e) => setSelectedFilter(e.target.value)}
                  >
                    <option value="trending">Trending</option>
                    <option value="recent">Recent</option>
                    <option value="following">Following</option>
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

            <div className="bg-white rounded-lg shadow-sm p-6 mb-8">
              <div className="flex gap-4">
                <img
                  src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80"
                  alt="Your avatar"
                  className="w-10 h-10 rounded-full object-cover"
                />
                <div className="flex-grow">
                  <Link
                    to="/community/users/create"
                    className="block w-full px-4 py-2 bg-gray-50 rounded-full text-gray-500 hover:bg-gray-100 transition-colors"
                  >
                    What's on your mind?
                  </Link>
                  <div className="flex gap-4 mt-4">
                    <button className="flex items-center gap-2 text-gray-600 hover:text-blue-500 transition-colors">
                      <ImageIcon size={20} />
                      <span>Photo</span>
                    </button>
                    <button className="flex items-center gap-2 text-gray-600 hover:text-blue-500 transition-colors">
                      <Smile size={20} />
                      <span>Feeling</span>
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <div className="space-y-6">
              {posts.map((post) => (
                <div key={post.id} className="bg-white rounded-lg shadow-sm hover:shadow-md transition-shadow">
                  <div className="p-6">
                    <div className="flex items-center gap-3 mb-4">
                      <img
                        src={post.user.avatar}
                        alt={post.user.name}
                        className="w-10 h-10 rounded-full object-cover"
                      />
                      <div className="flex-grow">
                        <div className="flex items-center gap-2">
                          <h3 className="font-semibold">{post.user.name}</h3>
                          {post.user.badge && (
                            <span className="px-2 py-1 bg-blue-100 text-blue-600 text-xs rounded-full">
                              {post.user.badge}
                            </span>
                          )}
                        </div>
                        <p className="text-sm text-gray-500">{post.timeAgo}</p>
                      </div>
                    </div>

                    <Link to={`/community/users/post/${post.id}`}>
                      <p className="mb-4 text-gray-800">{post.content}</p>
                      {post.image && (
                        <img
                          src={post.image}
                          alt="Post content"
                          className="w-full rounded-lg mb-4 hover:opacity-95 transition-opacity"
                        />
                      )}
                    </Link>

                    {post.tags && (
                      <div className="flex flex-wrap gap-2 mb-4">
                        {post.tags.map((tag) => (
                          <span
                            key={tag}
                            className="px-3 py-1 bg-gray-100 text-gray-600 rounded-full text-sm hover:bg-gray-200 cursor-pointer transition-colors"
                          >
                            #{tag}
                          </span>
                        ))}
                      </div>
                    )}

                    <div className="flex items-center justify-between text-gray-500 border-t border-b py-3">
                      <button className="flex items-center gap-1 hover:text-red-500 transition-colors">
                        <Heart size={20} />
                        <span>{post.likes}</span>
                      </button>
                      <button className="flex items-center gap-1 hover:text-blue-500 transition-colors">
                        <MessageCircle size={20} />
                        <span>{post.comments}</span>
                      </button>
                      <button className="flex items-center gap-1 hover:text-green-500 transition-colors">
                        <Share2 size={20} />
                        <span>{post.shares}</span>
                      </button>
                    </div>

                    <div className="mt-4 flex gap-3">
                      <img
                        src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80"
                        alt="Your avatar"
                        className="w-8 h-8 rounded-full object-cover"
                      />
                      <div className="flex-grow flex gap-2">
                        <input
                          type="text"
                          placeholder="Write a comment..."
                          className="flex-grow px-4 py-2 bg-gray-50 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-200 transition-all"
                          value={newComment}
                          onChange={(e) => setNewComment(e.target.value)}
                        />
                        <button 
                          className="text-blue-500 hover:text-blue-600 transition-colors"
                          onClick={() => setNewComment('')}
                        >
                          <Send size={20} />
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Right Sidebar */}
          <div className="md:col-span-1">
            <div className="bg-white rounded-lg shadow-sm p-6 sticky top-8">
              <div className="flex items-center gap-2 mb-6">
                <TrendingUp size={20} className="text-blue-500" />
                <h2 className="text-lg font-semibold">Trending Topics</h2>
              </div>
              <div className="space-y-4">
                {trendingTopics.map((topic) => (
                  <div
                    key={topic.name}
                    className="flex items-center justify-between hover:bg-gray-50 p-2 rounded-lg cursor-pointer transition-colors"
                  >
                    <span className="text-blue-500">#{topic.name}</span>
                    <span className="text-sm text-gray-500">{topic.posts} posts</span>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}