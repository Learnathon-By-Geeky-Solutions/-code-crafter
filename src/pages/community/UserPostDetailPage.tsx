import { useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { ArrowLeft, Heart, MessageCircle, Share2, Send } from 'lucide-react';

interface Comment {
  id: string;
  user: {
    name: string;
    avatar: string;
  };
  content: string;
  timeAgo: string;
  likes: number;
}

export function UserPostDetailPage() {
  const { id } = useParams<{ id: string }>();
  const [newComment, setNewComment] = useState('');

  const post = {
    id,
    user: {
      name: 'Sarah Johnson',
      avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80'
    },
    content: 'Just had an amazing experience with Dr. Smith at City Hospital. The new telemedicine service is so convenient! 🏥 #HealthcareTechnology',
    image: 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?auto=format&fit=crop&w=800&q=80',
    likes: 124,
    shares: 5,
    timeAgo: '2 hours ago'
  };

  const comments: Comment[] = [
    {
      id: '1',
      user: {
        name: 'John Doe',
        avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80'
      },
      content: 'I had a similar experience! The doctors there are really professional.',
      timeAgo: '1 hour ago',
      likes: 5
    },
    {
      id: '2',
      user: {
        name: 'Jane Smith',
        avatar: 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=150&h=150&q=80'
      },
      content: 'Thanks for sharing! I was looking for telemedicine services.',
      timeAgo: '30 minutes ago',
      likes: 3
    }
  ];

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto">
        <div className="flex items-center mb-8">
          <Link to="/community/users" className="flex items-center text-gray-600 hover:text-gray-800">
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back
          </Link>
        </div>

        <div className="bg-white rounded-lg shadow-sm">
          <div className="p-6">
            <div className="flex items-center gap-3 mb-4">
              <img
                src={post.user.avatar}
                alt={post.user.name}
                className="w-10 h-10 rounded-full object-cover"
              />
              <div>
                <h3 className="font-semibold">{post.user.name}</h3>
                <p className="text-sm text-gray-500">{post.timeAgo}</p>
              </div>
            </div>

            <p className="mb-4">{post.content}</p>
            {post.image && (
              <img
                src={post.image}
                alt="Post content"
                className="w-full rounded-lg mb-4"
              />
            )}

            <div className="flex items-center justify-between text-gray-500 border-t border-b py-2">
              <button className="flex items-center gap-1 hover:text-red-500">
                <Heart size={20} />
                <span>{post.likes}</span>
              </button>
              <button className="flex items-center gap-1 hover:text-blue-500">
                <MessageCircle size={20} />
                <span>{comments.length}</span>
              </button>
              <button className="flex items-center gap-1 hover:text-green-500">
                <Share2 size={20} />
                <span>{post.shares}</span>
              </button>
            </div>

            <div className="mt-6">
              <h3 className="font-semibold mb-4">Comments</h3>
              
              <div className="space-y-4 mb-6">
                {comments.map((comment) => (
                  <div key={comment.id} className="flex gap-3">
                    <img
                      src={comment.user.avatar}
                      alt={comment.user.name}
                      className="w-8 h-8 rounded-full object-cover"
                    />
                    <div className="flex-grow">
                      <div className="bg-gray-50 rounded-lg p-3">
                        <h4 className="font-semibold">{comment.user.name}</h4>
                        <p>{comment.content}</p>
                      </div>
                      <div className="flex items-center gap-4 mt-1 text-sm text-gray-500">
                        <button className="hover:text-red-500">Like</button>
                        <button className="hover:text-blue-500">Reply</button>
                        <span>{comment.timeAgo}</span>
                      </div>
                    </div>
                  </div>
                ))}
              </div>

              <div className="flex gap-3">
                <img
                  src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80"
                  alt="Your avatar"
                  className="w-8 h-8 rounded-full object-cover"
                />
                <div className="flex-grow flex gap-2">
                  <input
                    type="text"
                    placeholder="Write a comment..."
                    className="flex-grow px-4 py-2 bg-gray-50 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-200"
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                  />
                  <button 
                    className="text-blue-500 hover:text-blue-600"
                    onClick={() => setNewComment('')}
                  >
                    <Send size={20} />
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}