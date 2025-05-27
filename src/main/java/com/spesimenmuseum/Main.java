package com.spesimenmuseum;

import com.spesimenmuseum.dao.impl.*;
import com.spesimenmuseum.model.*;
import com.spesimenmuseum.service.impl.AuthService;
import com.spesimenmuseum.service.impl.SpecimenService;
import com.spesimenmuseum.util.ConsoleUtil;

import java.util.List;

public class Main {
    private static AuthService authService;
    private static SpecimenService specimenService;
    private static User loggedInUser = null;

    public static void main(String[] args) {
        RoleDAO roleDAO = new RoleDAO();
        UserDAO userDAO = new UserDAO(roleDAO);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        SpecialistDAO specialistDAO = new SpecialistDAO();
        VisitorDAO visitorDAO = new VisitorDAO();
        SpecimenDAO specimenDAO = new SpecimenDAO();

        authService = new AuthService(userDAO, roleDAO, employeeDAO, specialistDAO, visitorDAO);
        specimenService = new SpecimenService(specimenDAO);

        ConsoleUtil.blueColor();
        mainMenu();
        ConsoleUtil.resetColor();
    }

    private static void mainMenu() {
        while (true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.showInfoMessage("SELAMAT DATANG DI BIOMUSEUM", null);
            ConsoleUtil.delay(500);

            System.out.println("\n============= PEGAWAI =============");
            System.out.println("[1] Login Sebagai Pegawai");
            System.out.println("[2] Register Sebagai Pegawai");
            System.out.println("===================================");

            System.out.println("\n============ SPESIALIS ============");
            System.out.println("[3] Login Sebagai Spesialis");
            System.out.println("[4] Register Sebagai Spesialis");
            System.out.println("===================================");

            System.out.println("\n============ PENGUNJUNG ===========");
            System.out.println("[5] Login Sebagai Pengunjung");
            System.out.println("[6] Register Sebagai Pengunjung");
            System.out.println("===================================");

            System.out.println("\n[7] Exit");

            int menu = ConsoleUtil.getInputInt("\nMasukkan pilihan: ");
            switch (menu) {
                case 1: loginPegawai(); break;
                case 2: registerPegawai(); break;
                case 3: loginSpesialis(); break;
                case 4: registerSpesialis(); break;
                case 5: loginPengunjung(); break;
                case 6: registerPengunjung(); break;
                case 7:
                    ConsoleUtil.clearScreen();
                    ConsoleUtil.greenColor();
                    System.out.println("Terima kasih telah mengunjungi BIOMUSEUM");
                    ConsoleUtil.resetColor();
                    System.exit(0);
                    break;
                default:
                    ConsoleUtil.showErrorMessage("Pilihan tidak valid.");
            }
        }
    }

    private static void loginPegawai() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("LOGIN PEGAWAI", null);
        String username = ConsoleUtil.getInputString("Masukkan username: ");
        String password = ConsoleUtil.getInputString("Masukkan password: ");

        loggedInUser = authService.login(username, password);

        if (loggedInUser instanceof Employee) {
            ConsoleUtil.showSuccessMessage("LOGIN BERHASIL");
            menuPegawai((Employee) loggedInUser);
        } else {
            loggedInUser = null;
            ConsoleUtil.showErrorMessage("LOGIN GAGAL. Username atau password salah.");
        }
    }

    private static void registerPegawai() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("REGISTER PEGAWAI", null);
        String username = ConsoleUtil.getInputString("Masukkan username: ");
        String password = ConsoleUtil.getInputString("Masukkan password: ");
        String fullname = ConsoleUtil.getInputString("Masukkan nama lengkap: ");
        String email = ConsoleUtil.getInputString("Masukkan email: ");
        String phone = ConsoleUtil.getInputString("Masukkan nomor telepon: ");
        String jabatan = ConsoleUtil.getInputString("Masukkan jabatan: ");

        if (authService.registerEmployee(username, password, email, phone, fullname, jabatan)) {
            ConsoleUtil.showSuccessMessage("REGISTER PEGAWAI BERHASIL");
        } else {
            ConsoleUtil.showErrorMessage("REGISTER PEGAWAI GAGAL");
        }
    }

    private static void loginSpesialis() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("LOGIN SPESIALIS", null);
        String username = ConsoleUtil.getInputString("Masukkan username: ");
        String password = ConsoleUtil.getInputString("Masukkan password: ");

        loggedInUser = authService.login(username, password);

        if (loggedInUser instanceof Specialist) {
            ConsoleUtil.showSuccessMessage("LOGIN BERHASIL");
            menuSpesialis((Specialist) loggedInUser);
        } else {
            loggedInUser = null;
            ConsoleUtil.showErrorMessage("LOGIN GAGAL. Username atau password salah.");
        }
    }

    private static void registerSpesialis() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("REGISTER SPESIALIS", null);
        String username = ConsoleUtil.getInputString("Masukkan username: ");
        String password = ConsoleUtil.getInputString("Masukkan password: ");
        String fullname = ConsoleUtil.getInputString("Masukkan nama lengkap: ");
        String email = ConsoleUtil.getInputString("Masukkan email: ");
        String phone = ConsoleUtil.getInputString("Masukkan nomor telepon: ");
        String skill = ConsoleUtil.getInputString("Masukkan keahlian: ");
        String institution = ConsoleUtil.getInputString("Masukkan institusi: ");

        if (authService.registerSpecialist(username, password, email, phone, fullname, skill, institution)) {
            ConsoleUtil.showSuccessMessage("REGISTER SPESIALIS BERHASIL");
        } else {
            ConsoleUtil.showErrorMessage("REGISTER SPESIALIS GAGAL");
        }
    }

    private static void loginPengunjung() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("LOGIN PENGUNJUNG", null);
        String username = ConsoleUtil.getInputString("Masukkan username: ");
        String password = ConsoleUtil.getInputString("Masukkan password: ");

        loggedInUser = authService.login(username, password);

        if (loggedInUser instanceof Visitor) {
            ConsoleUtil.showSuccessMessage("LOGIN BERHASIL");
            menuPengunjung((Visitor) loggedInUser);
        } else {
            loggedInUser = null;
            ConsoleUtil.showErrorMessage("LOGIN GAGAL. Username atau password salah.");
        }
    }

    private static void registerPengunjung() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("REGISTER PENGUNJUNG", null);
        String username = ConsoleUtil.getInputString("Masukkan username: ");
        String password = ConsoleUtil.getInputString("Masukkan password: ");
        String fullname = ConsoleUtil.getInputString("Masukkan nama lengkap: ");
        String email = ConsoleUtil.getInputString("Masukkan email: ");
        String phone = ConsoleUtil.getInputString("Masukkan nomor telepon: ");
        String residence = ConsoleUtil.getInputString("Masukkan domisili: ");

        if (authService.registerVisitor(username, password, email, phone, fullname, residence)) {
            ConsoleUtil.showSuccessMessage("REGISTER PENGUNJUNG BERHASIL");
        } else {
            ConsoleUtil.showErrorMessage("REGISTER PENGUNJUNG GAGAL");
        }
    }

    private static void menuPegawai(Employee employee) {
        while (loggedInUser instanceof Employee) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.showInfoMessage("MENU UTAMA PEGAWAI", "Login sebagai: " + employee.getFullName() + " (" + employee.getPosition() + ")");

            System.out.println("\n[1] Tampilkan Data Diri Pegawai");
            System.out.println("[2] Tampilkan Daftar Spesimen ");
            System.out.println("[3] Tambah Spesimen");
            System.out.println("[4] Ubah Spesimen");
            System.out.println("[5] Hapus Spesimen");
            System.out.println("[6] Logout");

            int menu = ConsoleUtil.getInputInt("\nMasukkan pilihan: ");
            switch (menu) {
                case 1: displayEmployeeData(employee); break;
                case 2: showAllSpecimens(true); break;
                case 3: addSpecimen(); break;
                case 4: updateSpecimen(); break;
                case 5: deleteSpecimen(); break;
                case 6:
                    loggedInUser = null;
                    ConsoleUtil.showSuccessMessage("LOGOUT BERHASIL");
                    return;
                default: ConsoleUtil.showErrorMessage("Pilihan tidak valid.");
            }
        }
    }

    private static void displayEmployeeData(Employee employee) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("DATA DIRI PEGAWAI", null);
        employee.displaySelfDataHeader(); // Dari User class
        System.out.println("Nama Lengkap      : " + employee.getFullName());
        System.out.println("Jabatan           : " + employee.getPosition());
        System.out.println("Tanggal Bergabung : " + ConsoleUtil.localDateTimeToString(employee.getJoinedAt()));
        System.out.println("=========================================================================");
        ConsoleUtil.pressEnterToContinue();
    }

    private static void showAllSpecimens(boolean waitForEnter) {
        ConsoleUtil.clearScreen();
        List<Specimen> specimens = specimenService.getAllSpecimens();
        ConsoleUtil.showInfoMessage("DAFTAR SPESIMEN", specimens.isEmpty() ? "DATA SPESIMEN KOSONG" : null);

        if (specimens.isEmpty()) {
            if (waitForEnter) {
                ConsoleUtil.pressEnterToContinue();
            }
            return;
        }
        for (Specimen s : specimens) {
            System.out.println("\n-------------------------------------------------------------------------");
            System.out.println("ID                 : " + s.getId());
            System.out.println("Nama Spesimen      : " + s.getCommonName());
            System.out.println("Nama Ilmiah        : " + s.getScientificName());
            System.out.println("Jenis Spesimen     : " + s.getType());
            System.out.println("Metode Pengawetan  : " + s.getPreservationMethod());
            System.out.println("Tanggal Masuk      : " + ConsoleUtil.localDateTimeToString(s.getEntryAt()));
            System.out.println("Jumlah             : " + s.getQuantity());
            System.out.println("Deskripsi Singkat  : " + s.getDescription());
            System.out.println("Kondisi            : " + s.getCondition());
            System.out.println("Hasil Pemeriksaan  : " + s.getExaminationResult());
            System.out.println("Tanggal Pemeriksaan: " + ConsoleUtil.localDateTimeToString(s.getExaminedAt()));
            System.out.println("-------------------------------------------------------------------------");
        }
        if (waitForEnter) {
            ConsoleUtil.pressEnterToContinue();
        }
    }

    private static void addSpecimen() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("TAMBAH SPESIMEN", null);
        String commonName = ConsoleUtil.getInputString("Masukkan Nama Spesimen: ");
        String scientificName = ConsoleUtil.getInputString("Masukkan Nama Ilmiah: ");
        String type = ConsoleUtil.getInputString("Masukkan Jenis Spesimen: ");
        String preservationMethod = ConsoleUtil.getInputString("Masukkan Metode Pengawetan: ");
        int quantity = ConsoleUtil.getInputInt("Masukkan Jumlah: ");
        String description = ConsoleUtil.getInputString("Masukkan Deskripsi Singkat: ");

        if (specimenService.addSpecimen(commonName, scientificName, type, preservationMethod, quantity, description)) {
            ConsoleUtil.showSuccessMessage("BERHASIL MENAMBAHKAN SPESIMEN");
        } else {
            ConsoleUtil.showErrorMessage("GAGAL MENAMBAHKAN SPESIMEN (lihat log untuk detail).");
        }
    }

    private static void updateSpecimen() {
        showAllSpecimens(false);

        List<Specimen> specimens = specimenService.getAllSpecimens();
        if (specimens.isEmpty()) {
            return;
        }

        int id = ConsoleUtil.getInputInt("Masukkan ID Spesimen yang ingin diubah (0 untuk batal): ");
        if (id == 0) return;

        Specimen s = specimenService.getSpecimenById(id);
        if (s == null) {
            ConsoleUtil.showErrorMessage("Spesimen dengan ID " + id + " tidak ditemukan.");
            return;
        }

        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("UBAH SPESIMEN", "Mengubah Spesimen ID: " + s.getId() + " (" + s.getCommonName() + ")");
        String commonName = ConsoleUtil.getInputString("Nama Spesimen Baru ("+s.getCommonName()+", kosongkan jika tidak berubah): ");
        String scientificName = ConsoleUtil.getInputString("Nama Ilmiah Baru ("+s.getScientificName()+", kosongkan jika tidak berubah): ");
        String type = ConsoleUtil.getInputString("Jenis Spesimen Baru ("+s.getType()+", kosongkan jika tidak berubah): ");
        String preservationMethod = ConsoleUtil.getInputString("Metode Pengawetan Baru ("+s.getPreservationMethod()+", kosongkan jika tidak berubah): ");
        String jumlahStr = ConsoleUtil.getInputString("Jumlah Baru ("+s.getQuantity()+", kosongkan jika tidak berubah): ");
        String description = ConsoleUtil.getInputString("Deskripsi Baru ("+s.getDescription()+", kosongkan jika tidak berubah): ");
        String condition = ConsoleUtil.getInputString("Kondisi Baru (Baik/Rusak) ("+s.getCondition()+", kosongkan jika tidak berubah): ");

        int newQuantity;
        try {
            newQuantity = jumlahStr.isEmpty() ? s.getQuantity() : Integer.parseInt(jumlahStr);
        } catch (NumberFormatException e) {
            ConsoleUtil.showErrorMessage("Jumlah harus berupa angka.");
            return;
        }

        if (specimenService.updateSpecimen(id,
                commonName.isEmpty() ? s.getCommonName() : commonName,
                scientificName.isEmpty() ? s.getScientificName() : scientificName,
                type.isEmpty() ? s.getType() : type,
                preservationMethod.isEmpty() ? s.getPreservationMethod() : preservationMethod,
                newQuantity,
                description.isEmpty() ? s.getDescription() : description,
                condition.isEmpty() ? s.getCondition() : condition)) {
            ConsoleUtil.showSuccessMessage("BERHASIL MENGUBAH SPESIMEN");
        } else {
            ConsoleUtil.showErrorMessage("GAGAL MENGUBAH SPESIMEN (lihat log untuk detail).");
        }
    }

    private static void deleteSpecimen() {
        showAllSpecimens(false);

        List<Specimen> specimens = specimenService.getAllSpecimens();
        if (specimens.isEmpty()) {
            return;
        }

        int id = ConsoleUtil.getInputInt("Masukkan ID Spesimen yang ingin dihapus (0 untuk batal): ");
        if (id == 0) return;

        Specimen s = specimenService.getSpecimenById(id);
        if (s == null) {
            ConsoleUtil.showErrorMessage("Spesimen dengan ID " + id + " tidak ditemukan.");
            return;
        }
        String confirm = ConsoleUtil.getInputString("Apakah anda yakin ingin menghapus spesimen '" + s.getCommonName() + "'? (y/n): ");
        if (confirm.equalsIgnoreCase("y")) {
            if (specimenService.deleteSpecimen(id)) {
                ConsoleUtil.showSuccessMessage("BERHASIL MENGHAPUS SPESIMEN");
            } else {
                ConsoleUtil.showErrorMessage("GAGAL MENGHAPUS SPESIMEN (lihat log untuk detail).");
            }
        } else {
            ConsoleUtil.showInfoMessage("PENGHAPUSAN DIBATALKAN", null);
        }
    }

    private static void menuPengunjung(Visitor visitor) {
        while (loggedInUser instanceof Visitor) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.showInfoMessage("MENU PENGUNJUNG", "Login sebagai: " + visitor.getFullName());

            System.out.println("\n[1] Lihat Daftar Spesimen ");
            System.out.println("[2] Lihat Data Diri");
            System.out.println("[3] Ubah Data Diri");
            System.out.println("[4] Logout");

            int menu = ConsoleUtil.getInputInt("\nMasukkan pilihan: ");
            switch (menu) {
                case 1: showAllSpecimens(true); break;
                case 2: displayVisitorData(visitor); break;
                case 3: updateVisitorData(visitor); break;
                case 4:
                    loggedInUser = null;
                    ConsoleUtil.showSuccessMessage("LOGOUT BERHASIL");
                    return;
                default: ConsoleUtil.showErrorMessage("Pilihan tidak valid.");
            }
        }
    }

    private static void displayVisitorData(Visitor visitor) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("DATA DIRI PENGUNJUNG", null);
        visitor.displaySelfDataHeader();
        System.out.println("Nama Lengkap   : " + visitor.getFullName());
        System.out.println("Domisili       : " + visitor.getResidence());
        System.out.println("=========================================================================");
        ConsoleUtil.pressEnterToContinue();
    }

    private static void updateVisitorData(Visitor visitor) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("UBAH DATA DIRI PENGUNJUNG", "Data saat ini:");
        System.out.println("Nama Lengkap   : " + visitor.getFullName());
        System.out.println("Email          : " + visitor.getEmail());
        System.out.println("Nomor Telepon  : " + visitor.getPhoneNumber());
        System.out.println("Domisili       : " + visitor.getResidence());
        System.out.println("-------------------------------------------------------------------------");

        String newFullName = ConsoleUtil.getInputString("Nama Lengkap Baru (kosongkan jika tidak berubah): ");
        String newEmail = ConsoleUtil.getInputString("Email Baru (kosongkan jika tidak berubah): ");
        String newPhone = ConsoleUtil.getInputString("Nomor Telepon Baru (kosongkan jika tidak berubah): ");
        String newResidence = ConsoleUtil.getInputString("Domisili Baru (kosongkan jika tidak berubah): ");

        visitor.setFullName(newFullName.isEmpty() ? visitor.getFullName() : newFullName);
        visitor.setEmail(newEmail.isEmpty() ? visitor.getEmail() : newEmail);
        visitor.setPhoneNumber(newPhone.isEmpty() ? visitor.getPhoneNumber() : newPhone);
        visitor.setResidence(newResidence.isEmpty() ? visitor.getResidence() : newResidence);

        if (authService.updateVisitorProfile(visitor)) {
            ConsoleUtil.showSuccessMessage("DATA DIRI BERHASIL DIPERBARUI");
        } else {
            ConsoleUtil.showErrorMessage("GAGAL MEMPERBARUI DATA DIRI (lihat log untuk detail).");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private static void menuSpesialis(Specialist specialist) {
        while (loggedInUser instanceof Specialist) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.showInfoMessage("MENU SPESIALIS", "Login sebagai: " + specialist.getFullName() + " (" + specialist.getSkill() + ")");

            System.out.println("\n[1] Lihat Daftar Spesimen");
            System.out.println("[2] Periksa Spesimen");
            System.out.println("[3] Lihat Data Diri");
            System.out.println("[4] Logout");

            int menu = ConsoleUtil.getInputInt("\nMasukkan pilihan: ");
            switch (menu) {
                case 1: showAllSpecimens(true); break;
                case 2: examineSpecimenBySpecialist(); break;
                case 3: displaySpecialistData(specialist); break;
                case 4:
                    loggedInUser = null;
                    ConsoleUtil.showSuccessMessage("LOGOUT BERHASIL");
                    return;
                default: ConsoleUtil.showErrorMessage("Pilihan tidak valid.");
            }
        }
    }

    private static void displaySpecialistData(Specialist specialist) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("DATA DIRI SPESIALIS", null);
        specialist.displaySelfDataHeader();
        System.out.println("Nama Lengkap   : " + specialist.getFullName());
        System.out.println("Keahlian       : " + specialist.getSkill());
        System.out.println("Institusi      : " + specialist.getInstitution());
        System.out.println("=========================================================================");
        ConsoleUtil.pressEnterToContinue();
    }

    private static void examineSpecimenBySpecialist() {
        showAllSpecimens(false);

        List<Specimen> specimens = specimenService.getAllSpecimens();
        if (specimens.isEmpty()) {
            return;
        }

        int id = ConsoleUtil.getInputInt("Masukkan ID Spesimen yang ingin diperiksa (0 untuk batal): ");
        if (id == 0) return;

        Specimen s = specimenService.getSpecimenById(id);
        if (s == null) {
            ConsoleUtil.showErrorMessage("Spesimen dengan ID " + id + " tidak ditemukan.");
            return;
        }
        ConsoleUtil.clearScreen();
        ConsoleUtil.showInfoMessage("PERIKSA SPESIMEN", "Memeriksa Spesimen ID: " + s.getId() + " (" + s.getCommonName() + ")");
        System.out.println("Deskripsi          : " + s.getDescription());
        System.out.println("Kondisi Saat Ini   : " + s.getCondition());
        String hasilPemeriksaan = ConsoleUtil.getInputString("Masukkan Hasil Pemeriksaan/Rekomendasi: ");

        if (specimenService.examineSpecimen(id, hasilPemeriksaan)) {
            ConsoleUtil.showSuccessMessage("BERHASIL MENYIMPAN HASIL PEMERIKSAAN");
        } else {
            ConsoleUtil.showErrorMessage("GAGAL MENYIMPAN HASIL PEMERIKSAAN (lihat log untuk detail).");
        }
    }
}
