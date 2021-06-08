if (redis.call('hexists', KEYS[1], KEYS[2]) == 0) then
    return -1;
end;

local value = tonumber(redis.call('hget', KEYS[1], KEYS[2]));
if (value < 65536) then
    redis.call('hset', KEYS[1], KEYS[2], value + 65536);
    return 1;
else
    redis.call('hset', KEYS[1], KEYS[2], value - 65536);
    return 0;
end;