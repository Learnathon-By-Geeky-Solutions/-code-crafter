import { CheckCircle, Share2 } from 'lucide-react';
import { Link, useLocation } from 'react-router-dom';

export function BloodRequestConfirmationPage() {
  const location = useLocation();
  const formData = location.state?.formData;

  if (!formData) {
    return (
      <div className="container mx-auto px-4 py-8 text-center">
        <p className="text-gray-600">No post data found.</p>
        <Link to="/blood-donation/create-post" className="text-blue-500 hover:text-blue-600">
          Go back to create post
        </Link>
      </div>
    );
  }

  const postId = Math.random().toString(36).substr(2, 9).toUpperCase();
  const shareUrl = `${window.location.origin}/blood-donation/post/${postId}`;

  const handleShare = async () => {
    try {
      await navigator.share({
        title: formData.title,
        text: `Blood Needed: ${formData.bloodGroup} at ${formData.location}`,
        url: shareUrl
      });
    } catch (err) {
      console.error('Error sharing:', err);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto text-center">
        <div className="bg-white rounded-lg shadow-sm p-8">
          <div className="flex justify-center mb-6">
            <CheckCircle size={64} className="text-green-500" />
          </div>
          
          <h1 className="text-2xl font-bold mb-4">Blood Request Post Created!</h1>
          
          <div className="bg-green-50 rounded-lg p-6 mb-8">
            <p className="text-lg mb-4">
              Your blood request post has been published successfully. It is now visible to potential donors.
            </p>
            <div className="text-left space-y-2">
              <p><strong>Post ID:</strong> #{postId}</p>
              <p><strong>Title:</strong> {formData.title}</p>
              <p><strong>Blood Group:</strong> {formData.bloodGroup}</p>
              <p><strong>Location:</strong> {formData.location}</p>
              <p><strong>Urgency:</strong> {formData.urgency.charAt(0).toUpperCase() + formData.urgency.slice(1)}</p>
            </div>
          </div>

          <div className="bg-blue-50 rounded-lg p-6 mb-8">
            <h3 className="font-semibold mb-4">Share this request:</h3>
            <div className="flex items-center justify-center gap-4">
              <input
                type="text"
                value={shareUrl}
                readOnly
                className="flex-grow p-3 rounded-lg bg-white border-none"
              />
              <button
                onClick={handleShare}
                className="bg-blue-500 text-white p-3 rounded-lg hover:bg-blue-600 transition-colors"
              >
                <Share2 size={20} />
              </button>
            </div>
          </div>

          <div className="space-y-4">
            <p className="text-gray-600">
              Your post will be visible to all registered donors in your area.
              We will also notify matching donors via SMS.
            </p>
            <p className="text-gray-600">
              You can edit or remove your post at any time from your dashboard.
            </p>
          </div>

          <div className="mt-8 space-x-4">
            <button
              onClick={handleShare}
              className="inline-block bg-red-500 text-white px-6 py-2 rounded-full hover:bg-red-600 transition-colors"
            >
              Share Request
            </button>
            <Link
              to="/blood-donation"
              className="inline-block bg-gray-100 text-gray-700 px-6 py-2 rounded-full hover:bg-gray-200 transition-colors"
            >
              Return to Blood Donation
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}