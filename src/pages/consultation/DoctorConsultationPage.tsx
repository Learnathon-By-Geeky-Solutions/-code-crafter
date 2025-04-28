import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Video, FileUp, Download, X, Send, FileText } from 'lucide-react';

export function DoctorConsultationPage() {
  const { appointmentId } = useParams();
  const [prescription, setPrescription] = useState<File | null>(null);
  const [notes, setNotes] = useState('');
  const [showEndModal, setShowEndModal] = useState(false);

  const meetLink = "https://meet.google.com/abc-defg-hij"; // This would come from your backend

  const handlePrescriptionUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setPrescription(file);
    }
  };

  const handleEndConsultation = () => {
    // Handle ending consultation
    setShowEndModal(false);
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

            {/* Patient Documents */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="text-xl font-semibold mb-4">Patient Documents</h2>
              <div className="space-y-4">
                <div className="p-4 border border-gray-200 rounded-lg flex items-center justify-between">
                  <div className="flex items-center gap-3">
                    <FileText className="text-blue-500" size={24} />
                    <div>
                      <p className="font-medium">Previous Medical Report</p>
                      <p className="text-sm text-gray-500">Uploaded by patient</p>
                    </div>
                  </div>
                  <button className="text-blue-500 hover:text-blue-600">
                    <Download size={20} />
                  </button>
                </div>
              </div>
            </div>
          </div>

          {/* Sidebar */}
          <div className="space-y-6">
            {/* Patient Info */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="text-xl font-semibold mb-4">Patient Information</h2>
              <div className="space-y-3">
                <div>
                  <p className="text-sm text-gray-500">Name</p>
                  <p className="font-medium">John Doe</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Age</p>
                  <p className="font-medium">35 years</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Blood Group</p>
                  <p className="font-medium">A+</p>
                </div>
              </div>
            </div>

            {/* Prescription Upload */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="text-xl font-semibold mb-4">Upload Prescription</h2>
              <label className="block w-full p-4 border-2 border-dashed border-gray-300 rounded-lg cursor-pointer hover:border-blue-300 transition-colors">
                <div className="flex flex-col items-center">
                  <FileUp size={24} className="text-gray-400 mb-2" />
                  <p className="text-sm text-gray-500">Click to upload prescription</p>
                  <p className="text-xs text-gray-400">PDF format only</p>
                </div>
                <input
                  type="file"
                  accept=".pdf"
                  className="hidden"
                  onChange={handlePrescriptionUpload}
                />
              </label>
              {prescription && (
                <p className="mt-2 text-sm text-green-600">
                  Selected: {prescription.name}
                </p>
              )}
            </div>

            {/* Notes */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="text-xl font-semibold mb-4">Consultation Notes</h2>
              <textarea
                value={notes}
                onChange={(e) => setNotes(e.target.value)}
                className="w-full p-3 rounded-lg bg-gray-50 border-none focus:ring-2 focus:ring-blue-200"
                rows={4}
                placeholder="Add consultation notes..."
              />
              <button className="mt-4 w-full py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 flex items-center justify-center gap-2">
                <Send size={20} />
                Save Notes
              </button>
            </div>

            {/* End Consultation */}
            <button
              onClick={() => setShowEndModal(true)}
              className="w-full py-3 bg-red-500 text-white rounded-lg hover:bg-red-600"
            >
              End Consultation
            </button>
          </div>
        </div>
      </div>

      {/* End Consultation Modal */}
      {showEndModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-lg p-6 max-w-md w-full">
            <h3 className="text-xl font-semibold mb-4">End Consultation</h3>
            <p className="text-gray-600 mb-6">
              Are you sure you want to end this consultation? Make sure you have uploaded the prescription and saved your notes.
            </p>
            <div className="flex justify-end gap-4">
              <button
                onClick={() => setShowEndModal(false)}
                className="px-4 py-2 text-gray-600 hover:text-gray-800"
              >
                Cancel
              </button>
              <button
                onClick={handleEndConsultation}
                className="px-6 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600"
              >
                End Consultation
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}