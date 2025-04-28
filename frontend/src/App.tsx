import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Header } from './components/layout/Header';
import { Footer } from './components/layout/Footer';
import { HomePage } from './pages/HomePage';
import { AppointmentPage } from './pages/AppointmentPage';
import { SpecialtySelectionPage } from './pages/SpecialtySelectionPage';
import { DoctorListPage } from './pages/DoctorListPage';
import { DoctorDetailsPage } from './pages/DoctorDetailsPage';
import { BookTelemedicinePage } from './pages/BookTelemedicinePage';
import { PaymentPage } from './pages/PaymentPage';
import { ConfirmationPage } from './pages/ConfirmationPage';
import { HospitalListPage } from './pages/HospitalListPage';
import { FindPharmaciesPage } from './pages/FindPharmaciesPage';
import { DepartmentListPage } from './pages/DepartmentListPage';
import { BloodDonationPage } from './pages/BloodDonationPage';
import { BloodDonationPostsPage } from './pages/BloodDonationPostsPage';
import { BloodDonorPage } from './pages/BloodDonorPage';
import { BloodRecipientPage } from './pages/BloodRecipientPage';
import { CreateBloodRequestPage } from './pages/CreateBloodRequestPage';
import { BloodDonationHistoryPage } from './pages/BloodDonationHistoryPage';
import { BloodDonorConfirmationPage } from './pages/BloodDonorConfirmationPage';
import { BloodRecipientConfirmationPage } from './pages/BloodRecipientConfirmationPage';
import { BloodRequestConfirmationPage } from './pages/BloodRequestConfirmationPage';
import { BloodDonationPostPage } from './pages/BloodDonationPostPage';
import { UserCommunityPage } from './pages/community/UserCommunityPage';
import { DoctorCommunityPage } from './pages/community/DoctorCommunityPage';
import { CreateUserPostPage } from './pages/community/CreateUserPostPage';
import { UserPostDetailPage } from './pages/community/UserPostDetailPage';
import { CreateDoctorArticlePage } from './pages/community/CreateDoctorArticlePage';
import { DoctorArticleDetailPage } from './pages/community/DoctorArticleDetailPage';
import { BlogPostPage } from './pages/BlogPostPage';
import { AboutPage } from './pages/AboutPage';
import { ContactPage } from './pages/ContactPage';
import { LoginPage } from './pages/auth/LoginPage';
import { SignupPage } from './pages/auth/SignupPage';
import { ForgotPasswordPage } from './pages/auth/ForgotPasswordPage';
import { PatientSignupSuccessPage } from './pages/auth/PatientSignupSuccessPage';
import { DoctorSignupSuccessPage } from './pages/auth/DoctorSignupSuccessPage';
import { PatientDashboardPage } from './pages/patient/PatientDashboardPage';
import { DoctorDashboardPage } from './pages/doctor/DoctorDashboardPage';
import { DoctorAvailabilityPage } from './pages/doctor/DoctorAvailabilityPage';
import { AmbulanceServicePage } from './pages/AmbulanceServicePage';
import { AmbulanceListPage } from './pages/AmbulanceListPage';
import { AmbulanceDetailsPage } from './pages/AmbulanceDetailsPage';
import { MedicinePage } from './pages/medicine/MedicinePage';
import { PharmacyListPage } from './pages/medicine/PharmacyListPage';
import { PharmacyDetailsPage } from './pages/medicine/PharmacyDetailsPage';
import { MedicineSearchPage } from './pages/medicine/MedicineSearchPage';
import { CartPage } from './pages/medicine/CartPage';
import { MedicineCheckoutPage } from './pages/medicine/MedicineCheckoutPage';
import { MedicineConfirmationPage } from './pages/medicine/MedicineConfirmationPage';
import { MyAppointmentsPage } from './pages/user/MyAppointmentsPage';
import { MedicalRecordsPage } from './pages/user/MedicalRecordsPage';
import { SettingsPage } from './pages/user/SettingsPage';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-white flex flex-col">
        <Header />
        <main className="flex-grow">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />
            <Route path="/forgot-password" element={<ForgotPasswordPage />} />
            <Route path="/signup/patient/success" element={<PatientSignupSuccessPage />} />
            <Route path="/signup/doctor/success" element={<DoctorSignupSuccessPage />} />
            <Route path="/patient/dashboard" element={<PatientDashboardPage />} />
            <Route path="/doctor/dashboard" element={<DoctorDashboardPage />} />
            <Route path="/doctor/availability" element={<DoctorAvailabilityPage />} />
            <Route path="/ambulance" element={<AmbulanceServicePage />} />
            <Route path="/ambulance/book/:type" element={<AmbulanceListPage />} />
            <Route path="/ambulance/book/:type/:id" element={<AmbulanceDetailsPage />} />
            <Route path="/appointment" element={<AppointmentPage />} />
            <Route path="/appointment/:type" element={<SpecialtySelectionPage />} />
            <Route path="/appointment/:type/:specialty/doctors" element={<DoctorListPage />} />
            <Route path="/appointment/:type/:specialty/doctor/:id" element={<DoctorDetailsPage />} />
            <Route path="/appointment/:type/:specialty/doctor/:id/book" element={<BookTelemedicinePage />} />
            <Route path="/appointment/:type/:specialty/doctor/:id/payment" element={<PaymentPage />} />
            <Route path="/appointment/:type/:specialty/doctor/:id/confirmation" element={<ConfirmationPage />} />
            <Route path="/hospitals" element={<HospitalListPage />} />
            <Route path="/find-pharmacies" element={<FindPharmaciesPage />} />
            <Route path="/hospitals/:hospitalId/departments" element={<DepartmentListPage />} />
            <Route path="/hospitals/:hospitalId/departments/:departmentId/doctors" element={<DoctorListPage />} />
            <Route path="/hospitals/:hospitalId/departments/:departmentId/doctor/:id" element={<DoctorDetailsPage />} />
            <Route path="/hospitals/:hospitalId/departments/:departmentId/doctor/:id/book" element={<BookTelemedicinePage />} />
            <Route path="/hospitals/:hospitalId/departments/:departmentId/doctor/:id/payment" element={<PaymentPage />} />
            <Route path="/hospitals/:hospitalId/departments/:departmentId/doctor/:id/confirmation" element={<ConfirmationPage />} />
            <Route path="/blood-donation" element={<BloodDonationPage />} />
            <Route path="/blood-donation/posts" element={<BloodDonationPostsPage />} />
            <Route path="/blood-donation/donor" element={<BloodDonorPage />} />
            <Route path="/blood-donation/donor/confirmation" element={<BloodDonorConfirmationPage />} />
            <Route path="/blood-donation/recipient" element={<BloodRecipientPage />} />
            <Route path="/blood-donation/recipient/confirmation" element={<BloodRecipientConfirmationPage />} />
            <Route path="/blood-donation/create-post" element={<CreateBloodRequestPage />} />
            <Route path="/blood-donation/create-post/confirmation" element={<BloodRequestConfirmationPage />} />
            <Route path="/blood-donation/history" element={<BloodDonationHistoryPage />} />
            <Route path="/blood-donation/post/:id" element={<BloodDonationPostPage />} />
            <Route path="/community/users" element={<UserCommunityPage />} />
            <Route path="/community/users/create" element={<CreateUserPostPage />} />
            <Route path="/community/users/post/:id" element={<UserPostDetailPage />} />
            <Route path="/community/doctors" element={<DoctorCommunityPage />} />
            <Route path="/community/doctors/create" element={<CreateDoctorArticlePage />} />
            <Route path="/community/doctors/article/:id" element={<DoctorArticleDetailPage />} />
            <Route path="/blog/:id" element={<BlogPostPage />} />
            <Route path="/medicine" element={<MedicinePage />} />
            <Route path="/medicine/pharmacies" element={<PharmacyListPage />} />
            <Route path="/medicine/pharmacies/:id" element={<PharmacyDetailsPage />} />
            <Route path="/medicine/search" element={<MedicineSearchPage />} />
            <Route path="/medicine/cart" element={<CartPage />} />
            <Route path="/medicine/checkout" element={<MedicineCheckoutPage />} />
            <Route path="/medicine/confirmation" element={<MedicineConfirmationPage />} />
            <Route path="/appointments" element={<MyAppointmentsPage />} />
            <Route path="/records" element={<MedicalRecordsPage />} />
            <Route path="/settings" element={<SettingsPage />} />
            <Route path="/about" element={<AboutPage />} />
            <Route path="/contact" element={<ContactPage />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;