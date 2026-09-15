package model;

public class Member {
    private int memberId;
    private String name;
    private String phone;
    private String email;
    private static final int MIN_VALID_ID = 1;
    public Member(String name,String phone,String email){
        if (name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("member name can't be empty");
        }
        if(phone==null || phone.trim().isEmpty()){
            throw new IllegalArgumentException("member phone can't be empty");
        }
        if (email==null || !email.contains("@")){
            throw new IllegalArgumentException("This email isn't valid");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Member name must contain letters and spaces only");
        }
        if (!phone.matches("[0-9]+")) {
            throw new IllegalArgumentException("Phone must contain digits only");
        }
        this.name=name;
        this.phone=phone;
        this.email=email;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        if (memberId < MIN_VALID_ID) {
            throw new IllegalArgumentException("Member ID must be greater than 0");
        }

        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("member name can't be empty");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Member name must contain letters and spaces only");
        }
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(phone==null || phone.trim().isEmpty()){
            throw new IllegalArgumentException("member phone can't be empty");
        }
        if (!phone.matches("[0-9]+")) {
            throw new IllegalArgumentException("Phone must contain digits only");
        }
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email==null || !email.contains("@")){
            throw new IllegalArgumentException("this email isn,y valid");
        }
        this.email = email;
    }
}
