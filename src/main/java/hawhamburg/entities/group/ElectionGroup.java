package hawhamburg.entities.group;

import hawhamburg.entities.adventure.Adventurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ElectionGroup {
    private final List<Adventurer> members = new CopyOnWriteArrayList<>();
    private int id;
    private AtomicReference<Adventurer> leader = new AtomicReference<>();
    private final ReentrantReadWriteLock groupLock = new ReentrantReadWriteLock(true);

    public boolean setLeader(final Adventurer leader) {
        boolean success = false;
        if (members.contains(leader)) {
            this.leader.set(leader);
            success = true;
        }
        return success;
    }
    public void addMember(Adventurer adventurer){
        members.add(adventurer);
    }

    public Adventurer getLeader() {
        return leader.get();
    }

    public List<Adventurer> allMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<Adventurer> largerMembers(final Adventurer member) {
        final List<Adventurer> largerMembers = new ArrayList<>();
        if (groupLock.readLock().tryLock()) {
            try {
                for (final Adventurer otherMember : allMembers()) {
                    if (member.compareTo(otherMember) < 0) {
                        largerMembers.add(otherMember);
                    }
                }
            } finally {
                groupLock.readLock().unlock();
            }
        } else {
            System.out.println("Failed to acquire readLock for largerMembers");
        }
        return largerMembers;
    }
    public List<Adventurer> otherMembers(final Adventurer member) {
        final List<Adventurer> otherMembers = new ArrayList<>(members);
        otherMembers.remove(member);
        return otherMembers;
    }


    public Adventurer greatestIdMember() {
        Adventurer greatestIdMember = null;
        if (groupLock.readLock().tryLock()) {
            try {
                Collections.sort(members, new Comparator<Adventurer>() {
                    @Override
                    public int compare(Adventurer one, Adventurer two) {
                        return one.compareTo(two);
                    }
                });
                greatestIdMember = members.get(members.size() - 1);
            } finally {
                groupLock.readLock().unlock();
            }
        } else {
            System.out.println("Failed to acquire readLock for greatestIdMember");
        }
        return greatestIdMember;
    }

}
