import { Hero } from '../components/features/Hero';
import { Services } from '../components/features/Services';
import { AppointmentSection } from '../components/features/AppointmentSection';
import { BloodDonationSection } from '../components/features/BloodDonationSection';
import { AmbulanceSection } from '../components/features/AmbulanceSection';
import { Testimonials } from '../components/features/Testimonials';
import { Articles } from '../components/features/Articles';
import { MedicineSection } from '../components/features/MedicineSection';

export function HomePage() {
  return (
    <>
      <Hero />
      <Services />
      
      
      <BloodDonationSection />
      <AmbulanceSection />
      <Testimonials />
      
    </>
  );
}