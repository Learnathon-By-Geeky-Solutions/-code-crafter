import { Link } from 'react-router-dom';

export function Articles() {
  const articles = [
    {
      id: '1',
      image: "https://images.unsplash.com/photo-1576671081837-49000212a370?auto=format&fit=crop&w=800&q=80",
      title: "Disease detection, check up in the laboratory",
      description: "In this case, the role of the health laboratory is very important to do a disease detection...",
      author: {
        name: "Dr. Sarah Johnson",
        avatar: "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80",
        title: "Medical Laboratory Director"
      },
      date: "2024-03-15",
      readTime: "8 min read",
      category: "Laboratory Medicine",
      content: `In modern healthcare, the role of medical laboratories in disease detection and diagnosis cannot be overstated. These specialized facilities serve as the cornerstone of accurate medical diagnosis and treatment planning.

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
      5. Report generation`
    },
    {
      id: '2',
      image: "https://images.unsplash.com/photo-1631815589968-fdb09a223b1e?auto=format&fit=crop&w=800&q=80",
      title: "Herbal medicines that are safe for consumption",
      description: "Herbal medicine is very widely used at this time because of its very good for your health...",
      author: {
        name: "Dr. Michael Chen",
        avatar: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=150&h=150&q=80",
        title: "Natural Medicine Specialist"
      },
      date: "2024-03-14",
      readTime: "6 min read",
      category: "Natural Medicine"
    },
    {
      id: '3',
      image: "https://images.unsplash.com/photo-1505751172876-fa1923c5c528?auto=format&fit=crop&w=800&q=80",
      title: "Natural care for healthy facial skin",
      description: "A healthy lifestyle should start from now and also for your skin health...",
      author: {
        name: "Dr. Emily Rodriguez",
        avatar: "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=150&h=150&q=80",
        title: "Dermatologist"
      },
      date: "2024-03-13",
      readTime: "5 min read",
      category: "Skincare"
    }
  ];

  return (
    <div className="py-16 px-6 md:px-12">
      <h2 className="text-3xl font-bold text-center mb-12">Check out our latest article</h2>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        {articles.map((article) => (
          <div key={article.id} className="bg-white rounded-lg overflow-hidden shadow-sm hover:shadow-md transition-shadow">
            <img 
              src={article.image}
              alt={article.title}
              className="w-full h-48 object-cover"
            />
            <div className="p-6">
              <h3 className="text-xl font-semibold mb-2">{article.title}</h3>
              <p className="text-gray-600 mb-4">{article.description}</p>
              <div className="flex items-center gap-4 mb-4">
                <img
                  src={article.author.avatar}
                  alt={article.author.name}
                  className="w-10 h-10 rounded-full object-cover"
                />
                <div>
                  <p className="font-medium">{article.author.name}</p>
                  <p className="text-sm text-gray-500">{article.author.title}</p>
                </div>
              </div>
              <div className="flex items-center justify-between text-sm text-gray-500 mb-4">
                <span>{new Date(article.date).toLocaleDateString()}</span>
                <span>{article.readTime}</span>
              </div>
              <Link 
                to={`/blog/${article.id}`}
                className="inline-flex items-center gap-2 text-blue-500 font-medium hover:text-blue-600 transition-colors"
              >
                Read more
                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7" />
                </svg>
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}