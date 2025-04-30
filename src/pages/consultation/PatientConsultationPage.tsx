import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { Video, FileUp, Download, FileText } from 'lucide-react';

export function PatientConsultationPage() {
  const { appointmentId } = useParams();
  const [document, setDocument] = useState<File | null>(null);

  const meetLink = "https://meet.google.com/abc-defg-hij"; // This would come from your backend

  const handleDocumentUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setDocument(file);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-6xl mx-auto">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Main Content */}
          <div className="lg:col-span-2 space-y-6">
            {/* Video Section */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <div className="flex items-center justify-between mb-6">
                <h2 className="text-xl font-semibold">Video Consultation</h2>
                <a 
                  href={meetLink} 
                  target="_blank" 
                  rel="noopener noreferrer"
                  className="flex items-center gap-2 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
                >
                  <Video size={20} />
                  Join Meeting
                </a>
              </div>
              <div className="aspect-video bg-gray-100 rounded-lg flex items-center justify-center">
                <p className="text-gray-500">Video call will appear here</p>
              </div>
            </div>

            {/* Upload Documents */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="text-xl font-semibold mb-4">Upload Documents</h2>
              <label className="block w-full p-4 border-2 border-dashed border-gray-300 rounded-lg cursor-pointer hover:border-blue-300 transition-colors">
                <div className="flex flex-col items-center">
                  <FileUp size={24} className="text-gray-400 mb-2" />
                  <p className="text-sm text-gray-500">Click to upload medical documents</p>
                  <p className="text-xs text-gray-400">PDF, JPG, PNG (max. 10MB)</p>
                </div>
                <input
                  type="file"
                  accept=".pdf,.jpg,.jpeg,.png"
                  className="hidden"
                  onChange={handleDocumentUpload}
                />
              </label>
              {document && (
                <p className="mt-2 text-sm text-green-600">
                  Uploaded: {document.name}
                </p>
              )}
            </div>
          </div>

          {/* Sidebar */}
          <div className="space-y-6">
            {/* Doctor Info */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="text-xl font-semibold mb-4">Doctor Information</h2>
              <div className="flex items-center gap-4 mb-4">
                <img
                  src="https://images.unsplash.com/photo-1537368910025-700350fe46c7?auto=format&fit=crop&w=150&h=150"
                  alt="Doctor"
                  className="w-16 h-16 rounded-full object-cover"
                />
                <div>
                  <h3 className="font-semibold">Dr. Sarah Johnson</h3>
                  <p className="text-sm text-gray-500">Cardiologist</p>
                </div>
              </div>
              <div className="space-y-2">
                <p className="text-sm text-gray-500">Experience: 15 years</p>
                <p className="text-sm text-gray-500">Languages: English, Bengali</p>
              </div>
            </div>

            {/* Prescriptions */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="text-xl font-semibold mb-4">Prescriptions</h2>
              <div className="space-y-4">
                <div className="p-4 border border-gray-200 rounded-lg flex items-center justify-between">
                  <div className="flex items-center gap-3">
                    <FileText className="text-blue-500" size={24} />
                    <div>
                      <p className="font-medium">Today's Prescription</p>
                      <p className="text-sm text-gray-500">Uploaded by doctor</p>
                    </div>
                  </div>
                  <button className="text-blue-500 hover:text-blue-600">
                    <Download size={20} />
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