program Zad01;

uses
  SysUtils;

type
  list = array[1..1000] of Integer;

procedure ListInitialization(var tab: list; liczba_od: Integer; liczba_do: Integer; ile: Integer);
var
  i: Integer;
begin
  Randomize;

  if ile > 1000 then
    ile := 1000; 

  for i := 1 to ile do
    tab[i] := Random(liczba_do - liczba_od + 1) + liczba_od;
end;

procedure Sort(var tab: list; ile: Integer);
var
  i, j, temp: Integer;
begin
  for i := 1 to ile - 1 do
    for j := i + 1 to ile do
      if tab[i] > tab[j] then
      begin
        temp := tab[i];
        tab[i] := tab[j];
        tab[j] := temp;
      end; 
end;

procedure WriteTab(var tab: list; ile: Integer);
var
  i: Integer;
begin
  writeln('Tablica:');
  for i := 1 to ile do
    write(tab[i], ' ');
  writeln;
end;

var
  i: Integer;
  my_list, test_list: list;
  liczba_od, liczba_do, ile: Integer;

begin
    if ParamCount < 3 then
    begin
      writeln('Podaj dolny zakres, górny zakres oraz ilość liczb jako parametry wywołania programu.');
      Halt;
    end;

    
    liczba_od := StrToInt(ParamStr(1));
    liczba_do := StrToInt(ParamStr(2));
    ile := StrToInt(ParamStr(3));

    ListInitialization(my_list, liczba_od, liczba_do, ile);
    Writeln('Tablica przed sortowaniem:');
    WriteTab(my_list, ile);
    
    Sort(my_list, ile);
    
    Writeln;
    Writeln('Tablica po sortowaniu:');
    WriteTab(my_list, ile);

    writeln;writeln;

    ListInitialization(test_list, 1, 100, 10);
    Sort(test_list, 10);
    for i := 1 to 9 do
      if test_list[i] > test_list[i+1] then
      begin
        writeln('Test 1 FAILED');
        Break;
      end
      else if i = 9 then
      begin
        writeln('Test 1 PASSED');
      end;

    ListInitialization(test_list, 1, 1000, 1000);
    Sort(test_list, 1000);
    for i := 1 to 999 do
      if test_list[i] > test_list[i+1] then
      begin
        writeln('Test 2 FAILED');
        Break;
      end
      else if i = 999 then
      begin
        writeln('Test 2 PASSED');
      end;

end.