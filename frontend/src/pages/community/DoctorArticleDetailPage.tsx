import { useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { ArrowLeft, Bookmark, Heart, MessageCircle, Share2, Send, ThumbsUp, Reply, MoreHorizontal } from 'lucide-react';

interface Comment {
  id: string;
  user: {
    name: string;
    avatar: string;
    title?: string;
    isVerified?: boolean;
  };
  content: string;
  timeAgo: string;
  likes: number;
  replies?: Comment[];
}

export function DoctorArticleDetailPage() {
  const { id } = useParams<{ id: string }>();
  const [newComment, setNewComment] = useState('');
  const [expandedComments, setExpandedComments] = useState<string[]>([]);

  const article = {
    id,
    author: {
      name: 'Dr. Sarah Johnson',
      avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80',
      title: 'Cardiologist, City Heart Center',
      isVerified: true
    },
    title: 'Understanding Heart Health: A Comprehensive Guide for Patients',
    content: `Heart disease remains one of the leading causes of death globally. In this comprehensive guide, we'll explore preventive measures, risk factors, and lifestyle changes that can significantly improve heart health.

    The Basics of Heart Health
    Your heart is a remarkable organ that works tirelessly to keep you alive. Understanding how it functions and what it needs to stay healthy is crucial for maintaining overall well-being.

    Key Risk Factors
    - High blood pressure
    - High cholesterol
    - Smoking
    - Obesity
    - Physical inactivity
    - Diabetes
    - Family history

    Preventive Measures
    1. Maintain a healthy diet
    2. Exercise regularly
    3. Manage stress
    4. Get regular check-ups
    5. Monitor blood pressure
    6. Control cholesterol levels
    7. Quit smoking

    The Role of Exercise
    Regular physical activity is one of the best ways to strengthen your heart. Aim for at least 150 minutes of moderate-intensity aerobic exercise or 75 minutes of vigorous activity each week.`,
    image: 'https://images.unsplash.com/photo-1628348068343-c6a848d2b6dd?auto=format&fit=crop&w=800&q=80',
    readTime: '8 min read',
    likes: 342,
    shares: 89,
    tags: ['Cardiology', 'Heart Health', 'Prevention'],
    publishedAt: '2024-03-15'
  };

  const comments: Comment[] = [
    {
      id: '1',
      user: {
        name: 'Dr. Michael Chen',
        avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=150&h=150&q=80',
        title: 'Neurologist',
        isVerified: true
      },
      content: 'Excellent article! The connection between heart health and brain health cannot be overstated. Recent studies have shown a strong correlation between cardiovascular health and cognitive function.',
      timeAgo: '2 hours ago',
      likes: 15,
      replies: [
        {
          id: '1-1',
          user: {
            name: 'Dr. Sarah Johnson',
            avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80',
            title: 'Cardiologist',
            isVerified: true
          },
          content: 'Thank you, Dr. Chen! You\'re absolutely right. Would you be interested in collaborating on an article about this specific connection?',
          timeAgo: '1 hour ago',
          likes: 8
        }
      ]
    },
    {
      id: '2',
      user: {
        name: 'John Smith',
        avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80'
      },
      content: 'Very informative! Could you elaborate more on the role of genetics in heart disease? My family has a history of heart problems, and I\'d like to understand the preventive measures better.',
      timeAgo: '1 hour ago',
      likes: 8
    }
  ];

  const toggleCommentExpansion = (commentId: string) => {
    setExpandedComments(prev => 
      prev.includes(commentId)
        ? prev.filter(id => id !== commentId)
        : [...prev, commentId]
    );
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-3xl mx-auto">
        <div className="flex items-center mb-8">
          <Link to="/community/doctors" className="flex items-center text-gray-600 hover:text-gray-800">
            <ArrowLeft className="w-5 h-5 mr-2" />
            Back
          </Link>
        </div>

        <article className="bg-white rounded-lg shadow-sm overflow-hidden">
          <img
            src={article.image}
            alt={article.title}
            className="w-full h-80 object-cover"
          />
          
          <div className="p-8">
            <div className="flex items-center gap-3 mb-6">
              <img
                src={article.author.avatar}
                alt={article.author.name}
                className="w-12 h-12 rounded-full object-cover"
              />
              <div className="flex-grow">
                <div className="flex items-center gap-2">
                  <h3 className="font-semibold">{article.author.name}</h3>
                  {article.author.isVerified && (
                    <svg className="w-5 h-5 text-blue-500" viewBox="0 0 24 24" fill="currentColor">
                      <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                    </svg>
                  )}
                </div>
                <p className="text-sm text-gray-500">{article.author.title}</p>
              </div>
            </div>

            <h1 className="text-3xl font-bold mb-4">{article.title}</h1>

            <div className="flex items-center justify-between text-sm text-gray-500 mb-6">
              <div className="flex items-center gap-4">
                <span>{new Date(article.publishedAt).toLocaleDateString()}</span>
                <span>·</span>
                <span>{article.readTime}</span>
              </div>
              <button className="hover:text-blue-500 transition-colors">
                <Bookmark size={20} />
              </button>
            </div>

            <div className="prose max-w-none mb-8">
              {article.content.split('\n\n').map((paragraph, index) => (
                <p key={index} className="mb-4 text-gray-800">{paragraph}</p>
              ))}
            </div>

            <div className="flex flex-wrap gap-2 mb-6">
              {article.tags.map((tag) => (
                <span
                  key={tag}
                  className="px-3 py-1 bg-blue-50 text-blue-600 rounded-full text-sm hover:bg-blue-100 cursor-pointer transition-colors"
                >
                  {tag}
                </span>
              ))}
            </div>

            <div className="flex items-center justify-between text-gray-500 border-t border-b py-4">
              <div className="flex items-center gap-6">
                <button className="flex items-center gap-1 hover:text-red-500 transition-colors">
                  <Heart size={20} />
                  <span>{article.likes}</span>
                </button>
                <button className="flex items-center gap-1 hover:text-blue-500 transition-colors">
                  <MessageCircle size={20} />
                  <span>{comments.length}</span>
                </button>
                <button className="flex items-center gap-1 hover:text-green-500 transition-colors">
                  <Share2 size={20} />
                  <span>{article.shares}</span>
                </button>
              </div>
            </div>

            <div className="mt-8">
              <h3 className="font-semibold mb-6">Discussion ({comments.length})</h3>
              
              <div className="space-y-6 mb-8">
                {comments.map((comment) => (
                  <div key={comment.id} className="space-y-4">
                    <div className="flex gap-3">
                      <img
                        src={comment.user.avatar}
                        alt={comment.user.name}
                        className="w-10 h-10 rounded-full object-cover"
                      />
                      <div className="flex-grow">
                        <div className="bg-gray-50 rounded-lg p-4">
                          <div className="flex items-center justify-between mb-2">
                            <div className="flex items-center gap-2">
                              <h4 className="font-semibold">{comment.user.name}</h4>
                              {comment.user.isVerified && (
                                <svg className="w-4 h-4 text-blue-500" viewBox="0 0 24 24" fill="currentColor">
                                  <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                                </svg>
                              )}
                            </div>
                            <button className="text-gray-400 hover:text-gray-600">
                              <MoreHorizontal size={20} />
                            </button>
                          </div>
                          {comment.user.title && (
                            <p className="text-sm text-gray-500 mb-2">{comment.user.title}</p>
                          )}
                          <p className="text-gray-800">{comment.content}</p>
                        </div>
                        <div className="flex items-center gap-4 mt-2 text-sm">
                          <button className="flex items-center gap-1 text-gray-500 hover:text-blue-500 transition-colors">
                            <ThumbsUp size={16} />
                            <span>{comment.likes}</span>
                          </button>
                          <button className="flex items-center gap-1 text-gray-500 hover:text-blue-500 transition-colors">
                            <Reply size={16} />
                            <span>Reply</span>
                          </button>
                          <span className="text-gray-500">{comment.timeAgo}</span>
                          {comment.replies && (
                            <button
                              className="text-blue-500 hover:text-blue-600"
                              onClick={() => toggleCommentExpansion(comment.id)}
                            >
                              {expandedComments.includes(comment.id) ? 'Hide replies' : 'Show replies'}
                            </button>
                          )}
                        </div>
                      </div>
                    </div>

                    {comment.replies && expandedComments.includes(comment.id) && (
                      <div className="ml-12 space-y-4">
                        {comment.replies.map((reply) => (
                          <div key={reply.id} className="flex gap-3">
                            <img
                              src={reply.user.avatar}
                              alt={reply.user.name}
                              className="w-8 h-8 rounded-full object-cover"
                            />
                            <div className="flex-grow">
                              <div className="bg-gray-50 rounded-lg p-3">
                                <div className="flex items-center gap-2 mb-1">
                                  <h4 className="font-semibold">{reply.user.name}</h4>
                                  {reply.user.isVerified && (
                                    <svg className="w-4 h-4 text-blue-500" viewBox="0 0 24 24" fill="currentColor">
                                      <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                                    </svg>
                                  )}
                                </div>
                                {reply.user.title && (
                                  <p className="text-sm text-gray-500 mb-2">{reply.user.title}</p>
                                )}
                                <p className="text-gray-800">{reply.content}</p>
                              </div>
                              <div className="flex items-center gap-4 mt-2 text-sm">
                                <button className="flex items-center gap-1 text-gray-500 hover:text-blue-500 transition-colors">
                                  <ThumbsUp size={16} />
                                  <span>{reply.likes}</span>
                                </button>
                                <button className="flex items-center gap-1 text-gray-500 hover:text-blue-500 transition-colors">
                                  <Reply size={16} />
                                  <span>Reply</span>
                                </button>
                                <span className="text-gray-500">{reply.timeAgo}</span>
                              </div>
                            </div>
                          </div>
                        ))}
                      </div>
                    )}
                  </div>
                ))}
              </div>

              <div className="flex gap-3">
                <img
                  src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=150&h=150&q=80"
                  alt="Your avatar"
                  className="w-10 h-10 rounded-full object-cover"
                />
                <div className="flex-grow flex gap-2">
                  <input
                    type="text"
                    placeholder="Add to the discussion"
                    className="flex-grow px-4 py-2 bg-gray-50 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-200 transition-all"
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
        </article>
      </div>
    </div>
  );
}