import { ArrowLeft, Calendar, Clock, Share2, Heart, MessageCircle } from 'lucide-react';
import { Link, useParams } from 'react-router-dom';
import { useState } from 'react';

interface Comment {
  id: string;
  user: {
    name: string;
    avatar: string;
    title?: string;
  };
  content: string;
  timeAgo: string;
  likes: number;
}

export function BlogPostPage() {
  const { id } = useParams<{ id: string }>();
  const [newComment, setNewComment] = useState('');
  const [isLiked, setIsLiked] = useState(false);

  // This would come from an API in a real application
  const article = {
    id,
    title: "Disease detection, check up in the laboratory",
    image: "https://images.unsplash.com/photo-1576671081837-49000212a370?auto=format&fit=crop&w=1200&q=80",
    content: `
      In modern healthcare, the role of medical laboratories in disease detection and diagnosis cannot be overstated. These specialized facilities serve as the cornerstone of accurate medical diagnosis and treatment planning.

      The Importance of Laboratory Testing
      Medical laboratory testing plays a crucial role in:
      • Disease diagnosis and confirmation
      • Treatment monitoring
      • Disease prevention
      • Research and development

      Advanced Testing Methods
      Today's laboratories employ state-of-the-art technology and methods including:
      • Molecular diagnostics
      • Flow cytometry
      • Mass spectrometry
      • Next-generation sequencing

      The laboratory testing process typically involves several key steps:
      1. Sample collection
      2. Sample processing
      3. Analysis
      4. Result interpretation
      5. Report generation

      Quality Control Measures
      To ensure accurate results, laboratories implement rigorous quality control measures:
      • Regular equipment calibration
      • Standardized procedures
      • Staff training and certification
      • External quality assessment

      The Future of Laboratory Medicine
      The field continues to evolve with emerging technologies:
      • Artificial Intelligence in diagnostics
      • Point-of-care testing
      • Automated systems
      • Personalized medicine approaches

      Patient Benefits
      Modern laboratory services offer numerous advantages:
      • Faster results
      • More accurate diagnosis
      • Less invasive testing methods
      • Better treatment monitoring
      
      The integration of laboratory services with other healthcare functions has revolutionized patient care, enabling more precise and personalized treatment approaches.
    `,
    author: {
      name: "Dr. Sarah Johnson",
      title: "Medical Laboratory Director",
      image: "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80",
      bio: "Dr. Sarah Johnson is a renowned medical laboratory director with over 15 years of experience in diagnostic medicine."
    },
    date: "2024-03-15",
    readTime: "8 min read",
    category: "Laboratory Medicine",
    tags: ["Healthcare", "Laboratory", "Diagnosis", "Medical Technology"],
    likes: 245,
    shares: 89,
    relatedArticles: [
      {
        id: '2',
        title: "Herbal medicines that are safe for consumption",
        image: "https://images.unsplash.com/photo-1631815589968-fdb09a223b1e?auto=format&fit=crop&w=300&h=200&q=80",
        author: "Dr. Michael Chen"
      },
      {
        id: '3',
        title: "Natural care for healthy facial skin",
        image: "https://images.unsplash.com/photo-1505751172876-fa1923c5c528?auto=format&fit=crop&w=300&h=200&q=80",
        author: "Dr. Emily Rodriguez"
      }
    ]
  };

  const comments: Comment[] = [
    {
      id: '1',
      user: {
        name: 'Dr. Michael Chen',
        avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=150&h=150&q=80',
        title: 'Neurologist'
      },
      content: 'Excellent article! The importance of laboratory testing in modern medicine cannot be overstated. This comprehensive guide will be very helpful for both medical professionals and patients.',
      timeAgo: '2 hours ago',
      likes: 15
    },
    {
      id: '2',
      user: {
        name: 'Emily Wilson',
        avatar: 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=150&h=150&q=80'
      },
      content: 'Very informative! I appreciate how you broke down the complex processes into understandable sections. The quality control measures section was particularly enlightening.',
      timeAgo: '3 hours ago',
      likes: 8
    }
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <div className="relative h-[400px] overflow-hidden">
        <img
          src={article.image}
          alt={article.title}
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-b from-black/60 to-black/80" />
        <div className="absolute inset-0 flex items-center">
          <div className="container mx-auto px-6">
            <div className="max-w-4xl mx-auto text-center text-white">
              <Link to="/" className="inline-flex items-center text-white/80 hover:text-white mb-6">
                <ArrowLeft className="w-5 h-5 mr-2" />
                Back to Home
              </Link>
              <h1 className="text-4xl md:text-5xl font-bold mb-4">{article.title}</h1>
              <div className="flex items-center justify-center gap-6 text-white/90">
                <span className="flex items-center gap-2">
                  <Calendar size={20} />
                  {new Date(article.date).toLocaleDateString()}
                </span>
                <span className="flex items-center gap-2">
                  <Clock size={20} />
                  {article.readTime}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-6 py-12">
        <div className="max-w-4xl mx-auto">
          <div className="bg-white rounded-2xl shadow-sm overflow-hidden">
            <div className="p-8">
              {/* Author Info */}
              <div className="flex items-center gap-4 mb-8 pb-8 border-b">
                <img
                  src={article.author.image}
                  alt={article.author.name}
                  className="w-16 h-16 rounded-full object-cover"
                />
                <div>
                  <h3 className="font-semibold text-lg">{article.author.name}</h3>
                  <p className="text-gray-600">{article.author.title}</p>
                  <p className="text-sm text-gray-500 mt-1">{article.author.bio}</p>
                </div>
              </div>

              {/* Article Content */}
              <div className="prose max-w-none">
                {article.content.split('\n\n').map((paragraph, index) => {
                  if (paragraph.trim().endsWith(':')) {
                    return <h2 key={index} className="text-2xl font-bold mt-8 mb-4">{paragraph}</h2>;
                  }
                  if (paragraph.startsWith('•')) {
                    return (
                      <ul key={index} className="list-disc pl-6 my-4">
                        {paragraph.split('\n').map((item, i) => (
                          <li key={i} className="text-gray-700">{item.replace('•', '').trim()}</li>
                        ))}
                      </ul>
                    );
                  }
                  if (paragraph.match(/^\d\./)) {
                    return (
                      <ol key={index} className="list-decimal pl-6 my-4">
                        {paragraph.split('\n').map((item, i) => (
                          <li key={i} className="text-gray-700">{item.replace(/^\d\./, '').trim()}</li>
                        ))}
                      </ol>
                    );
                  }
                  return <p key={index} className="text-gray-700 mb-4">{paragraph}</p>;
                })}
              </div>

              {/* Tags */}
              <div className="flex flex-wrap gap-2 mt-8 pt-8 border-t">
                {article.tags.map((tag) => (
                  <span
                    key={tag}
                    className="px-3 py-1 bg-gray-100 text-gray-600 rounded-full text-sm hover:bg-gray-200 cursor-pointer"
                  >
                    #{tag}
                  </span>
                ))}
              </div>

              {/* Social Actions */}
              <div className="flex items-center justify-between mt-8 pt-8 border-t">
                <div className="flex items-center gap-6">
                  <button 
                    className={`flex items-center gap-2 ${
                      isLiked ? 'text-red-500' : 'text-gray-600 hover:text-red-500'
                    } transition-colors`}
                    onClick={() => setIsLiked(!isLiked)}
                  >
                    <Heart size={24} fill={isLiked ? 'currentColor' : 'none'} />
                    <span>{article.likes + (isLiked ? 1 : 0)}</span>
                  </button>
                  <button className="flex items-center gap-2 text-gray-600 hover:text-blue-500 transition-colors">
                    <MessageCircle size={24} />
                    <span>{comments.length}</span>
                  </button>
                  <button className="flex items-center gap-2 text-gray-600 hover:text-green-500 transition-colors">
                    <Share2 size={24} />
                    <span>{article.shares}</span>
                  </button>
                </div>
                <span className="text-gray-500">{article.category}</span>
              </div>

              {/* Comments Section */}
              <div className="mt-12">
                <h3 className="text-xl font-semibold mb-6">Comments</h3>
                
                <div className="space-y-6 mb-8">
                  {comments.map((comment) => (
                    <div key={comment.id} className="flex gap-4">
                      <img
                        src={comment.user.avatar}
                        alt={comment.user.name}
                        className="w-12 h-12 rounded-full object-cover"
                      />
                      <div className="flex-grow">
                        <div className="bg-gray-50 rounded-lg p-4">
                          <div className="flex items-center gap-2 mb-2">
                            <h4 className="font-semibold">{comment.user.name}</h4>
                            {comment.user.title && (
                              <span className="text-sm text-gray-500">
                                • {comment.user.title}
                              </span>
                            )}
                          </div>
                          <p className="text-gray-700">{comment.content}</p>
                        </div>
                        <div className="flex items-center gap-4 mt-2 text-sm">
                          <button className="text-gray-500 hover:text-blue-500 transition-colors">
                            Like • {comment.likes}
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
                    className="w-12 h-12 rounded-full object-cover"
                  />
                  <div className="flex-grow">
                    <textarea
                      placeholder="Write a comment..."
                      className="w-full p-4 bg-gray-50 rounded-lg border-none focus:ring-2 focus:ring-blue-200 resize-none"
                      rows={3}
                      value={newComment}
                      onChange={(e) => setNewComment(e.target.value)}
                    />
                    <button className="mt-2 px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors">
                      Post Comment
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* Related Articles */}
          <div className="mt-12">
            <h3 className="text-2xl font-bold mb-6">Related Articles</h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {article.relatedArticles.map((related) => (
                <Link
                  key={related.id}
                  to={`/blog/${related.id}`}
                  className="bg-white rounded-lg shadow-sm overflow-hidden hover:shadow-md transition-shadow"
                >
                  <img
                    src={related.image}
                    alt={related.title}
                    className="w-full h-48 object-cover"
                  />
                  <div className="p-4">
                    <h4 className="font-semibold mb-2">{related.title}</h4>
                    <p className="text-gray-500">By {related.author}</p>
                  </div>
                </Link>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}