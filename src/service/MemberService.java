package service;

import dao.MemberDAO;
import model.Member;

public class MemberService {
    private MemberDAO memberDAO;

    private static final int MIN_VALID_ID = 1;

    public MemberService(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public void addMember(Member member){
        if (member==null){
            throw new IllegalArgumentException( "Member cannot be null" );
        }
        if (memberDAO.emailExists(member.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        memberDAO.addMember(member);
    }

    public void updateMember(Member member){
        if (member==null){
            throw new IllegalArgumentException( "Member cannot be null" );
        }
        if (!memberDAO.memberExists(member.getMemberId())){
            throw new IllegalArgumentException( "Member does not exist" );
        }
        if (memberDAO.emailExistsForAnotherMember(member.getEmail(), member.getMemberId())){
            throw new IllegalArgumentException("Email already exists");
        }
        memberDAO.updateMember(member);
    }

    public void deleteMember(int memberID){
        if (memberID<MIN_VALID_ID){
            throw new IllegalArgumentException( "Member ID must be greater than 0" );
        }
        if (!memberDAO.memberExists(memberID)) {
            throw new IllegalArgumentException( "Member does not exist" );
        }
        if (memberDAO.hasBorrowing(memberID)){
            throw new IllegalArgumentException( "Cannot delete member because they have borrowings" );
        }
        memberDAO.deleteMember(memberID);
    }

    public void getAllMembers(){
        memberDAO.getAllMembers();
    }
}
