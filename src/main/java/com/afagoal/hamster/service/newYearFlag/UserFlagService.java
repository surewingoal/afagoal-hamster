package com.afagoal.hamster.service.newYearFlag;

import com.afagoal.hamster.dao.newYearFlag.FlagDao;
import com.afagoal.hamster.dao.newYearFlag.GoodSayingDao;
import com.afagoal.hamster.dao.newYearFlag.UserRecordDao;
import com.afagoal.hamster.dto.newYearFlag.UserFlagsDto;
import com.afagoal.hamster.entity.newYearFlag.Flag;
import com.afagoal.hamster.entity.newYearFlag.GoodSaying;
import com.afagoal.hamster.entity.newYearFlag.UserRecord;
import com.afagoal.utildto.cache.CacheDto;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@Service
public class UserFlagService {

    @PostConstruct
    public void init() {
        cacheFlags();
        cacheGoodSaying();
    }

    private Map<String, CacheDto<UserFlagsDto>> userRecordCache = new ConcurrentHashMap();
    private Map<Long, Flag> flagCache = new ConcurrentHashMap();
    private Map<Long, GoodSaying> goodSayingCache = new ConcurrentHashMap();

    @Autowired
    private UserRecordDao userRecordDao;
    @Autowired
    private FlagDao flagDao;
    @Autowired
    private GoodSayingDao goodSayingDao;

    public UserFlagsDto findUserFlags(String userName, Byte gender) {

        CacheDto<UserFlagsDto> cacheDto = userRecordCache.get(userKey(userName, gender));
        if (null != cacheDto) {
            return cacheDto.getValue();
        }
        UserRecord userRecord = userRecordDao.getByUserNameAndGender(userName, gender);

        if (null != userRecord) {
            List<Long> flagIds = Arrays.stream(userRecord.getFlags()
                    .split(","))
                    .map(id -> Long.valueOf(id))
                    .collect(Collectors.toList());
            Set<Flag> flags = flagIds.stream()
                    .map(flagId -> flagCache.get(flagId))
                    .collect(Collectors.toSet());

            return UserFlagsDto.instance(userRecord, flags);
        }

        return null;
    }


    @Transactional
    public UserFlagsDto createUserFlags(String userName, Byte gender) {
        UserRecord userRecord = new UserRecord();
        userRecord.setGender(gender);
        userRecord.setUserName(userName);

        Long[] flagIds = new Long[5];
        Set<Flag> userFlags = new HashSet();
        for (int i = 0; i < 5; i++) {
            Flag flag = selectFlag(userName, gender);
            flagIds[i] = flag.getId();
            userFlags.add(flag);
        }

        String flagStr = StringUtils.join(flagIds, ",");
        userRecord.setFlags(flagStr);

        GoodSaying goodSaying = selectGoodSaying();

        userRecord.setGoodSayingId(goodSaying.getId());
        userRecord.setGoodSaying(goodSaying);

        userRecordDao.save(userRecord);

        return UserFlagsDto.instance(userRecord, userFlags);
    }

    private Flag selectFlag(String userName, Byte gender) {
        Long[] keys = flagCache.keySet().toArray(new Long[flagCache.size()]);
        Random random = new Random();
        Long randomKey = keys[random.nextInt(keys.length)];
        return flagCache.get(randomKey);
    }

    private GoodSaying selectGoodSaying() {
        Long[] keys = goodSayingCache.keySet().toArray(new Long[goodSayingCache.size()]);
        Random random = new Random();
        Long randomKey = keys[random.nextInt(keys.length)];
        return goodSayingCache.get(randomKey);
    }


    public void cacheUserFlags(UserFlagsDto userFlagsDto) {
        userRecordCache.put(userKey(userFlagsDto.getUserName(), userFlagsDto.getGender()),
                CacheDto.instance(userFlagsDto, (long) (1 * 60)));
    }

    private String userKey(String userName, Byte gender) {
        String genderStr = null == gender ? "" : String.valueOf(gender);
        String key = StringUtils.join(userName, "::", genderStr);
        return key;
    }

    protected void cacheUserRecode() {
        Iterator<Map.Entry<String, CacheDto<UserFlagsDto>>> iterator = userRecordCache.entrySet()
                .iterator();
        while (iterator.hasNext()) {
            CacheDto cacheDto = iterator.next().getValue();

            if (LocalDateTime.now().isAfter(cacheDto.getExpire())) {
                iterator.remove();
            }
        }
    }

    protected void cacheFlags() {
        flagCache.clear();
        List<Flag> allFlag = flagDao.getAll();
        allFlag.forEach(flag -> flagCache.put(flag.getId(), flag));
    }

    protected void cacheGoodSaying() {
        goodSayingCache.clear();
        List<GoodSaying> allGoodSaying = goodSayingDao.getAll();
        allGoodSaying.forEach(goodSaying -> goodSayingCache.put(goodSaying.getId(), goodSaying));
    }
}
