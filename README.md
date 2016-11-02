# UnblockmeSearch
An stupid Unblockme searcher, use dfs.
Support limit steps, status...
Bad effort:
pz1: 4 steps
pz2: 200+ steps

Improve @ 20161103
1. move more than 1 cells each step
2. check for least steps
3. effort:
pz1:
min step: 1
==========================================================================
move    2[0, 2, 2, 1]->{     Right 4} =    2[4, 2, 2, 1]
pz2:
min step: 20
==========================================================================
move    6[1, 2, 2, 1]->{     Right 1} =    6[2, 2, 2, 1]
move    9[1, 3, 1, 2]->{        Up 2} =    9[1, 1, 1, 2]
move   10[2, 3, 2, 1]->{      Left 1} =   10[1, 3, 2, 1]
move   13[3, 4, 1, 2]->{        Up 1} =   13[3, 3, 1, 2]
move   12[0, 5, 2, 1]->{     Right 4} =   12[4, 5, 2, 1]
move    8[0, 3, 1, 2]->{      Down 1} =    8[0, 4, 1, 2]
move   13[3, 3, 1, 2]->{      Down 1} =   13[3, 4, 1, 2]
move   10[1, 3, 2, 1]->{     Right 1} =   10[2, 3, 2, 1]
move    9[1, 1, 1, 2]->{      Down 3} =    9[1, 4, 1, 2]
move    6[2, 2, 2, 1]->{      Left 1} =    6[1, 2, 2, 1]
move   10[2, 3, 2, 1]->{      Left 2} =   10[0, 3, 2, 1]
move   13[3, 4, 1, 2]->{        Up 3} =   13[3, 1, 1, 2]
move   11[4, 3, 2, 1]->{      Left 2} =   11[2, 3, 2, 1]
move   12[4, 5, 2, 1]->{      Left 2} =   12[2, 5, 2, 1]
move   14[4, 4, 2, 1]->{      Left 2} =   14[2, 4, 2, 1]
move    4[5, 0, 1, 3]->{      Down 3} =    4[5, 3, 1, 3]
move    3[3, 0, 2, 1]->{     Right 1} =    3[4, 0, 2, 1]
move    7[4, 1, 1, 2]->{      Down 3} =    7[4, 4, 1, 2]
move   13[3, 1, 1, 2]->{        Up 1} =   13[3, 0, 1, 2]
move    6[1, 2, 2, 1]->{     Right 3} =    6[4, 2, 2, 1]
