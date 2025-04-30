import { useState } from 'react';
import { ArrowLeft, MapPin, Phone, Clock, Heart, Share2, MessageCircle, Send } from 'lucide-react';
import { Link, useParams } from 'react-router-dom';

interface Comment {
  id: string;
  user: {
    name: string;
    avatar: string;
  };
  content: string;
  timeAgo: string;
}

export function BloodDonationPostPage() {
  const { id } = useParams<{ id: string }>();
  const [newComment, setNewComment] = useState('');

  // This would come from an API in a real application
  const post = {
    id,
    title: 'Need A+ blood in Mirpur 10',
    content: 'Urgently need 2 bags of A+ blood for my father who is undergoing surgery at Popular Hospital, Mirpur 10. The surgery is scheduled for tomorrow morning at 9 AM.',
    author: {
      name: 'Kabir Ahmed',
      avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80'
    },
    bloodGroup: 'A+',
    requiredUnits: 2,
    location: 'Popular Hospital, Mirpur 10, Dhaka',
    contactNumber: '+880 1234-567890',
    requiredDate: '2024-03-20',
    requiredTime: '09:00 AM',
    urgency: 'Urgent',
    image: 'https://images.unsplash.com/photo-1615461066841-6116e61058f4?auto=format&fit=crop&w=800&h=400&q=80',
    createdAt: '2024-03-19T10:00:00Z',
    status: 'Active',
    responses: 3,
    shares: 12
  };

  const comments: Comment[] = [
    {
      id: '1',
      user: {
        name: 'Sarah Khan',
        avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80'
      },
      content: 'I am A+ blood donor and available. Please check your inbox for my contact details.',
      timeAgo: '5 minutes ago'
    },
    {
      id: '2',
      user: {
        name: 'Rahim Mia',
        avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=150&h=150&q=80'
      },
      content: 'I have shared this in my blood donor group. Hope you find donors soon.',
      timeAgo: '15 minutes ago'
    }
  ];

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-4xl mx-auto">
        <div className="flex items-center mb-8">
          <Link to="/blood-donation" className="flex items-center text-gray-600 hover:text-gray-800">
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back to Blood Donation
          </Link>
        </div>

        <div className="bg-white rounded-xl shadow-sm overflow-hidden">
          {/* Post Header */}
          <div className="relative h-[300px]">
            <img
              src={post.image}
              alt="Blood Donation"
              className="w-full h-full object-cover"
            />
            <div className="absolute inset-0 bg-gradient-to-t from-black/75 via-black/50 to-transparent">
              <div className="absolute bottom-6 left-6 right-6">
                <div className="flex items-center gap-4 mb-4">
                  <img
                    src={post.author.avatar}
                    alt={post.author.name}
                    className="w-12 h-12 rounded-full border-2 border-white"
                  />
                  <div>
                    <h3 className="text-white font-medium">{post.author.name}</h3>
                    <p className="text-white/80 text-sm">
                      {new Date(post.createdAt).toLocaleDateString()}
                    </p>
                  </div>
                </div>
                <h1 className="text-3xl font-bold text-white">{post.title}</h1>
              </div>
            </div>
          </div>

          <div className="p-6">
            {/* Blood Request Details */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
              <div className="space-y-4">
                <div>
                  <h3 className="text-sm font-medium text-gray-500">Blood Group</h3>
                  <p className="text-2xl font-bold text-red-500">{post.bloodGroup}</p>
                </div>
                <div>
                  <h3 className="text-sm font-medium text-gray-500">Required Units</h3>
                  <p className="text-lg">{post.requiredUnits} bags</p>
                </div>
                <div>
                  <h3 className="text-sm font-medium text-gray-500">Required Date & Time</h3>
                  <p className="text-lg">
                    {new Date(post.requiredDate).toLocaleDateString()} at {post.requiredTime}
                  </p>
                </div>
              </div>

              <div className="space-y-4">
                <div className="flex items-center gap-2">
                  <MapPin className="text-gray-400" size={20} />
                  <span>{post.location}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Phone className="text-gray-400" size={20} />
                  <span>{post.contactNumber}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Clock className="text-gray-400" size={20} />
                  <span>{post.status}</span>
                </div>
              </div>
            </div>

            {/* Post Content */}
            <div className="mb-8">
              <p className="text-gray-700">{post.content}</p>
            </div>

            {/* Action Buttons */}
            <div className="flex items-center justify-between border-t border-b py-4 mb-8">
              <div className="flex items-center gap-6">
                <button className="flex items-center gap-2 text-gray-600 hover:text-red-500 transition-colors">
                  <Heart size={24} />
                  <span>Interested</span>
                </button>
                <button className="flex items-center gap-2 text-gray-600 hover:text-blue-500 transition-colors">
                  <MessageCircle size={24} />
                  <span>{comments.length} Comments</span>
                </button>
                <button className="flex items-center gap-2 text-gray-600 hover:text-green-500 transition-colors">
                  <Share2 size={24} />
                  <span>{post.shares} Shares</span>
                </button>
              </div>
              <span className={`px-3 py-1 rounded-full text-sm ${
                post.urgency === 'Urgent'
                  ? 'bg-red-100 text-red-600'
                  : 'bg-yellow-100 text-yellow-600'
              }`}>
                {post.urgency}
              </span>
            </div>

            {/* Comments Section */}
            <div>
              <h3 className="font-semibold mb-6">Comments</h3>
              
              <div className="space-y-6 mb-8">
                {comments.map((comment) => (
                  <div key={comment.id} className="flex gap-4">
                    <img
                      src={comment.user.avatar}
                      alt={comment.user.name}
                      className="w-10 h-10 rounded-full object-cover"
                    />
                    <div className="flex-grow">
                      <div className="bg-gray-50 rounded-lg p-4">
                        <h4 className="font-medium mb-1">{comment.user.name}</h4>
                        <p className="text-gray-700">{comment.content}</p>
                      </div>
                      <div className="flex items-center gap-4 mt-2 text-sm">
                        <button className="text-gray-500 hover:text-blue-500 transition-colors">
                          Like
                        </button>
                        <button className="text-gray-500 hover:text-blue-500 transition-colors">
                          Reply
                        </button>
                        <span className="text-gray-500">{comment.timeAgo}</span>
                      </div>
                    </div>
                  </div>
                ))}
              </div>

              {/* Comment Form */}
              <div className="flex gap-4">
                <img
                  src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80"
                  alt="Your avatar"
                  className="w-10 h-10 rounded-full object-cover"
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
                    className="text-blue-500 hover:text-blue-600 transition-colors"
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