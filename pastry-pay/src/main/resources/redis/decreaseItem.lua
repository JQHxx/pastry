if (redis.call('hexists', KEYS[1], KEYS[2]) == 0) then
    return -1;
end;

local value = redis.call('hincrby', KEYS[1], KEYS[2], -1);
if (value == 0 or value == 65536) then
    redis.call('hdel', KEYS[1], KEYS[2]);
    return 0;
else
    return value;
end;