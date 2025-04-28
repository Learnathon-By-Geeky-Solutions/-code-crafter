import { useEffect, useState } from 'react';
import { ArrowLeft, MapPin, Phone, Clock, Heart, Share2, MessageCircle, Send } from 'lucide-react';
import { Link, useParams } from 'react-router-dom';
import { api } from '../lib/api';

interface Comment {
  userFirstName: string;
  userLastName: string;
  content: string;
}

interface Post {
  id: number;
  patientName: string;
  bloodType: string;
  quantity: number;
  hospitalName: string;
  contactNumber: string;
  description: string;
  date: string;
  upazila: {
    name: string;
    district: {
      name: string;
      division: {
        name: string;
      };
    };
  };
  bloodRequestPostCommentResponses: Comment[];
}

export function BloodDonationPostPage() {
  const { id } = useParams<{ id: string }>();
  const [post, setPost] = useState<Post | null>(null);
  const [newComment, setNewComment] = useState('');
  const [comments, setComments] = useState<Comment[]>([]);

  useEffect(() => {
    if (id) {
      fetchPost();
    }
  }, [id]);

  const fetchPost = async () => {
    try {
      const { data: baseResponse } = await api.get(`/api/v1/blood/blood-request-post-page`);
      if (baseResponse.code === 'XS0001') {
        const foundPost = baseResponse.data.content.find((p: Post) => p.id === parseInt(id!));
        if (foundPost) {
          setPost(foundPost);
          setComments(foundPost.bloodRequestPostCommentResponses || []);
        }
      }
    } catch (error) {
      console.error('Error fetching post:', error);
    }
  };

  const handleShare = () => {
    const shareUrl = window.location.href;
    const shareText = `Urgent Blood Request: ${post?.bloodType.replace('_POS', '+').replace('_NEG', '-')} needed at ${post?.hospitalName}`;

    if (navigator.share) {
      navigator.share({
        title: 'Blood Donation Request',
        text: shareText,
        url: shareUrl,
      })
      .then(() => console.log('Shared successfully'))
      .catch((error) => console.error('Error sharing:', error));
    } else {
      // fallback: manual options
      const fb = `https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(shareUrl)}`;
      const twitter = `https://twitter.com/intent/tweet?url=${encodeURIComponent(shareUrl)}&text=${encodeURIComponent(shareText)}`;
      const whatsapp = `https://api.whatsapp.com/send?text=${encodeURIComponent(shareText + ' ' + shareUrl)}`;
      const shareOptions = window.confirm("Share to Facebook? Click Cancel for WhatsApp.");

      if (shareOptions) {
        window.open(fb, '_blank');
      } else {
        window.open(whatsapp, '_blank');
      }
    }
  };

  const handleComment = async () => {
    if (!newComment.trim() || !post) return;

    try {
      const payload = {
        postId: post.id,
        comment: newComment.trim(),
      };
      const { data: baseResponse } = await api.post('/api/v1/blood/create-comment', payload);

      if (baseResponse.code === 'XS0001') {
        setComments([...comments, { userFirstName: "You", userLastName: "", content: newComment.trim() }]);
        setNewComment('');
      }
    } catch (error) {
      console.error('Error creating comment:', error);
    }
  };

  if (!post) return <div className="text-center py-12">Loading...</div>;

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
              src="https://images.unsplash.com/photo-1615461066841-6116e61058f4?auto=format&fit=crop&w=800&q=80"
              alt="Blood Donation"
              className="w-full h-full object-cover"
            />
            <div className="absolute inset-0 bg-gradient-to-t from-black/75 via-black/50 to-transparent">
              <div className="absolute bottom-6 left-6 right-6">
                <h1 className="text-3xl font-bold text-white">{post.bloodType.replace('_POS', '+').replace('_NEG', '-')} Blood Needed</h1>
              </div>
            </div>
          </div>

          <div className="p-6">
            {/* Blood Request Details */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
              <div className="space-y-4">
                <div>
                  <h3 className="text-sm font-medium text-gray-500">Blood Group</h3>
                  <p className="text-2xl font-bold text-red-500">{post.bloodType.replace('_POS', '+').replace('_NEG', '-')}</p>
                </div>
                <div>
                  <h3 className="text-sm font-medium text-gray-500">Required Units</h3>
                  <p className="text-lg">{post.quantity} bags</p>
                </div>
                <div>
                  <h3 className="text-sm font-medium text-gray-500">Required Date</h3>
                  <p className="text-lg">{new Date(post.date).toLocaleDateString()}</p>
                </div>
              </div>

              <div className="space-y-4">
                <div className="flex items-center gap-2">
                  <MapPin className="text-gray-400" size={20} />
                  <span>{post.hospitalName}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Phone className="text-gray-400" size={20} />
                  <span>{post.contactNumber}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Clock className="text-gray-400" size={20} />
                  <span>Active Request</span>
                </div>
              </div>
            </div>

            {/* Post Description */}
            <div className="mb-8">
              <p className="text-gray-700">{post.description}</p>
            </div>

            {/* Action Buttons */}
            <div className="flex items-center justify-between border-t border-b py-4 mb-8">
              <div className="flex items-center gap-6">
                <button className="flex items-center gap-2 text-gray-600 hover:text-blue-500 transition-colors">
                  <MessageCircle size={24} />
                  <span>{comments.length} Comments</span>
                </button>
                <button
                  onClick={handleShare}
                  className="flex items-center gap-2 text-gray-600 hover:text-green-500 transition-all active:scale-90"
                >
                  <Share2 size={24} />
                  <span>Share</span>
                </button>
              </div>
              <span className="px-3 py-1 rounded-full text-sm bg-red-100 text-red-600">
                Active
              </span>
            </div>

            {/* Comments Section */}
            <div>
              <h3 className="font-semibold mb-6">Comments</h3>

              <div className="space-y-6 mb-8">
                {comments.map((comment, index) => (
                  <div key={index} className="flex gap-4">
                    <img
                      src="https://images.unsplash.com/photo-1607746882042-944635dfe10e?auto=format&fit=crop&w=150&h=150&q=80"
                      alt="avatar"
                      className="w-10 h-10 rounded-full object-cover"
                    />
                    <div className="flex-grow">
                      <div className="bg-gray-50 rounded-lg p-4">
                        <h4 className="font-medium mb-1">{comment.userFirstName} {comment.userLastName}</h4>
                        <p className="text-gray-700">{comment.content}</p>
                      </div>
                    </div>
                  </div>
                ))}
              </div>

              {/* Comment Form */}
              <div className="flex gap-4">
                <img
                  src="https://images.unsplash.com/photo-1607746882042-944635dfe10e?auto=format&fit=crop&w=150&h=150&q=80"
                  alt="your avatar"
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
                    onClick={handleComment}
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
