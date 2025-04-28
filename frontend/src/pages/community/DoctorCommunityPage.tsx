import { Link } from 'react-router-dom';
import { Bookmark, Heart, MessageCircle, Share2 } from 'lucide-react';

interface Article {
  id: string;
  author: {
    name: string;
    avatar: string;
    title: string;
  };
  title: string;
  excerpt: string;
  image: string;
  readTime: string;
  likes: number;
  comments: number;
  shares: number;
  tags: string[];
  publishedAt: string;
}

const articles: Article[] = [
  {
    id: '1',
    author: {
      name: 'Dr. Sarah Johnson',
      avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80',
      title: 'Cardiologist, City Heart Center'
    },
    title: 'Understanding Heart Health: A Comprehensive Guide for Patients',
    excerpt: 'Heart disease remains one of the leading causes of death globally. In this comprehensive guide, we\'ll explore preventive measures, risk factors, and lifestyle changes that can significantly improve heart health...',
    image: 'https://images.unsplash.com/photo-1628348068343-c6a848d2b6dd?auto=format&fit=crop&w=800&q=80',
    readTime: '8 min read',
    likes: 342,
    comments: 56,
    shares: 89,
    tags: ['Cardiology', 'Heart Health', 'Prevention'],
    publishedAt: '2024-03-15'
  },
  {
    id: '2',
    author: {
      name: 'Dr. Michael Chen',
      avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=150&h=150&q=80',
      title: 'Neurologist, Brain & Spine Institute'
    },
    title: 'Latest Advances in Neurology: What Patients Should Know',
    excerpt: 'The field of neurology is rapidly evolving with new treatments and technologies emerging regularly. This article discusses recent breakthroughs and their implications for patient care...',
    image: 'https://images.unsplash.com/photo-1559757175-5700dde675bc?auto=format&fit=crop&w=800&q=80',
    readTime: '12 min read',
    likes: 256,
    comments: 34,
    shares: 67,
    tags: ['Neurology', 'Medical Research', 'Treatment'],
    publishedAt: '2024-03-14'
  }
];

export function DoctorCommunityPage() {
  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-4xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-2xl font-bold">Medical Articles</h1>
          <Link
            to="/community/doctors/create"
            className="bg-blue-500 text-white px-6 py-2 rounded-full hover:bg-blue-600 transition-colors"
          >
            Write Article
          </Link>
        </div>

        <div className="space-y-8">
          {articles.map((article) => (
            <article key={article.id} className="bg-white rounded-lg shadow-sm overflow-hidden">
              <img
                src={article.image}
                alt={article.title}
                className="w-full h-64 object-cover"
              />
              
              <div className="p-6">
                <div className="flex items-center gap-3 mb-4">
                  <img
                    src={article.author.avatar}
                    alt={article.author.name}
                    className="w-12 h-12 rounded-full object-cover"
                  />
                  <div>
                    <h3 className="font-semibold">{article.author.name}</h3>
                    <p className="text-sm text-gray-500">{article.author.title}</p>
                  </div>
                </div>

                <Link to={`/community/doctors/article/${article.id}`}>
                  <h2 className="text-2xl font-bold mb-3 hover:text-blue-600">
                    {article.title}
                  </h2>
                </Link>

                <p className="text-gray-600 mb-4">{article.excerpt}</p>

                <div className="flex flex-wrap gap-2 mb-4">
                  {article.tags.map((tag) => (
                    <span
                      key={tag}
                      className="px-3 py-1 bg-blue-50 text-blue-600 rounded-full text-sm"
                    >
                      {tag}
                    </span>
                  ))}
                </div>

                <div className="flex items-center justify-between text-gray-500 border-t pt-4">
                  <div className="flex items-center gap-6">
                    <button className="flex items-center gap-1 hover:text-red-500">
                      <Heart size={20} />
                      <span>{article.likes}</span>
                    </button>
                    <button className="flex items-center gap-1 hover:text-blue-500">
                      <MessageCircle size={20} />
                      <span>{article.comments}</span>
                    </button>
                    <button className="flex items-center gap-1 hover:text-green-500">
                      <Share2 size={20} />
                      <span>{article.shares}</span>
                    </button>
                  </div>
                  <div className="flex items-center gap-4">
                    <span className="text-sm">{article.readTime}</span>
                    <button className="hover:text-blue-500">
                      <Bookmark size={20} />
                    </button>
                  </div>
                </div>
              </div>
            </article>
          ))}
        </div>
      </div>
    </div>
  );
}